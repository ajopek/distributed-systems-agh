#define _BSD_SOURCE
#define _DEFAULT_SOURCE

#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <assert.h>
#include <signal.h>
#include <signal.h>
#include <sys/epoll.h>
#include <fcntl.h>
#include <sys/poll.h>
#include "common.h"


int next_socket_fd;
int prev_socket_fd;
char name[MAX_NAME_LEN];
int protocol;
int port;
struct sockaddr_in *prev_address = NULL;
struct sockaddr_in next_address;
int next_port;
int prev_port;
int have_token;
int logger_fd;
token token1;
struct sockaddr_in my_address;

void
cleanup (int sig)
{
    CHECK (shutdown (next_socket_fd, SHUT_RDWR), "Shutdown error of client socket.\n");
    CHECK (close (next_socket_fd), "Error closing client socket.\n");
    CHECK (shutdown (prev_socket_fd, SHUT_RDWR), "Shutdown error of client socket.\n");
    CHECK (close (prev_socket_fd), "Error closing client socket.\n");
    fprintf (stderr, "Cleanup finished, exiting.\n");
    exit(0);
}

// UDP Utils -------------

void
get_socket_udp(int *fd) {
    CHECK(*fd = socket(AF_INET, SOCK_DGRAM, 0), "Error creating socket.\n");
}


void
get_socket_udp_bind(int *fd, int port) {

    struct sockaddr_in addr;

    CHECK_EXIT(*fd = socket(AF_INET, SOCK_DGRAM, 0), "Error creating socket.\n")

    addr.sin_family = AF_INET;
    addr.sin_addr.s_addr = htonl(INADDR_ANY); // get local random local address
    addr.sin_port = htons((uint16_t) port);

    CHECK_EXIT(bind(*fd, (struct sockaddr *) &addr, sizeof(addr)), "Error biding socket.\n")
}

struct sockaddr_in
udp_send(int fd, int port, in_addr_t addr, void *msg, size_t size) {

    struct sockaddr_in socket_addr;

    socket_addr.sin_family = AF_INET;
    socket_addr.sin_port = htons((uint16_t) port);
    socket_addr.sin_addr.s_addr = addr;

    sendto(fd,
           msg,
           size,
           0,
           (const struct sockaddr *) &socket_addr,
           sizeof(socket_addr));

    return socket_addr;
}


// TCP Utlis --------------

struct sockaddr_in
get_socket_tcp_bind(int *fd, struct sockaddr_in addr) {

    *fd = socket(AF_INET, SOCK_STREAM, 0);
    CHECK_EXIT(fd, "Error creating socket.\n")
    //fcntl(*fd, F_SETFL, O_NONBLOCK);
    CHECK_EXIT(bind(*fd, (struct sockaddr *) &addr, sizeof(addr)), "Error biding socket.\n")

    CHECK_EXIT(listen(*fd, 10), "Error listining to socket.\n")

    return addr;
}

void
get_socket_tcp(int *fd) {

    *fd = socket(AF_INET, SOCK_STREAM, 0);
    if (*fd == -1) {
        printf("can't create socket\n");
        exit(1);
    }
}

void
accept_connection_tcp(int socket_fd, int *socket_client, struct sockaddr_in *addr) {

    addr = calloc(1, sizeof(struct sockaddr_in));
    socklen_t len = sizeof(addr);
    *socket_client = accept(socket_fd, (struct sockaddr *) addr, &len);
    //CHECK_EXIT(socket_client, "Error accepting connection.\n")
}

void
connect_tcp(int fd, struct sockaddr_in addr) {
    while(connect(fd, (const struct sockaddr *) &addr, sizeof(addr)) == -1);

}

//

int
main(int argc, char *argv[])
{
    signal(SIGINT, cleanup);

    if (argc != 8) {
        fprintf(stderr, "Wrong number of args.");
        fprintf(stderr, "Usage: name addr port next-addr next-port have-token protocol(tcp=0|udp=1).");
        exit(1);
    }

    char next_addr[100];
    char addr[100];
    sscanf(argv[1], "%s", name);
    sscanf(argv[2], "%s", addr);
    sscanf(argv[3], "%d", &port);
    sscanf(argv[4], "%s", next_addr);
    sscanf(argv[5], "%d", &next_port);
    sscanf(argv[6], "%d", &have_token);
    sscanf(argv[7], "%d", &protocol);

    next_address.sin_port = htons(next_port);
    struct in_addr addr1;
    inet_aton(next_addr, &addr1);
    next_address.sin_addr = addr1;
    next_address.sin_family = AF_INET;

    my_address.sin_port = htons(port);
    struct in_addr addr2;
    inet_aton(addr, &addr2);
    my_address.sin_addr = addr2;
    my_address.sin_family = AF_INET;

    get_socket_udp(&logger_fd);


    int socket_cli;
    if(protocol == 0) {
        struct sockaddr_in addr;
        get_socket_tcp_bind(&prev_socket_fd, my_address);
        get_socket_tcp(&next_socket_fd);

        if(have_token == 0) {
            connect_tcp(next_socket_fd, next_address);
            send(next_socket_fd, &my_address, sizeof(my_address), 0);
            struct sockaddr_in new_conn_addr;
            accept_connection_tcp(prev_socket_fd, &socket_cli, prev_address);
            fprintf(stderr, "got connection.\n");
        } else {
            struct sockaddr_in new_conn_addr;
            accept_connection_tcp(prev_socket_fd, &socket_cli, prev_address);
            read(socket_cli, &new_conn_addr, sizeof(struct sockaddr_in));
            fprintf(stderr, "got connection.\n");
            connect_tcp(next_socket_fd, next_address);
        }
        sleep(1);
    }

    // network loop
    int new_conn = 0;
    struct sockaddr_in new_conn_addr;
    int new_socket_cli = -1;
    int new_client_socket = -1;
    int new_conn_read = 0;

    while (1) {

        if (have_token == 0) {
            if (protocol == 0) { // TCP

                fcntl(prev_socket_fd, F_SETFL, O_NONBLOCK);
                struct sockaddr_in *addr = calloc(1, sizeof(struct sockaddr_in));
                accept_connection_tcp(prev_socket_fd, &new_socket_cli, addr);
                if(new_socket_cli > 0) {
                        new_conn = 1;
                        new_conn_read = 1;
                        free(prev_address);
                        prev_address = addr;
                        new_client_socket = new_socket_cli;
                }

                if(new_conn_read) {
                    read(new_socket_cli, &new_conn_addr, sizeof(struct sockaddr_in));
                    new_conn_read = 0;
                } else {
                    if(read(socket_cli, &token1, sizeof(token1)) > 0) {
                        if(token1.from == name) {
                           token1.free = 1;
                        }
                        if (token1.type == MESSAGE  && !token1.free) {
                            if (strcmp(token1.to, name) == 0) {
                                fprintf(stderr, "Got msg: %s, from: %s \n", token1.msg.content, token1.from);
                                token1.free = 1;
                            }
                        } else if (token1.type == NEW_CLIENT  && !token1.free) {
                                next_address = token1.new_conn_addr;
                                //next_port = token1.port;
                                close(next_socket_fd);
                                get_socket_tcp(&next_socket_fd);
                                connect_tcp(next_socket_fd, next_address);
                                token1.free = 1;
                        }
                        have_token = 1;
                        sleep(1);
                    }

                }

                //close(socket_cli);

            } else {
                printf("invalid protocol\n");
                exit(1);
            }

        }
        if(have_token) {



            udp_send(logger_fd, LOGGER_PORT, inet_addr("224.0.0.1"),
                          name, sizeof(name));

            if(new_conn) {
                token1.free = 0;
                token1.from_addr = my_address;
                strcpy(token1.from, name);
                token1.new_conn_addr = new_conn_addr;
                token1.type = NEW_CLIENT;
                new_conn = 0;
                socket_cli = new_client_socket;
            } else {
                char to[MAX_NAME_LEN];
                char msg_con[100];
                struct pollfd mypoll = { STDIN_FILENO, POLLIN|POLLPRI };
                if(poll(&mypoll, 1, 2000)) {
                    scanf("%s %s", to, msg_con);
                    //scanf("a1 hello", "%s %s", to, msg_con);
                    if (to != NULL) {
                        token1.free = 0;
                        token1.from_addr = my_address;
                        strcpy(token1.to, to);
                        strcpy(token1.from, name);
                        token1.msg.len = strlen(msg_con);
                        strcpy(token1.msg.content, msg_con);
                        token1.type = MESSAGE;
                        new_conn = 0;
                        fprintf(stdout, "send.\n");
                    }
                }
            }

            //connect_tcp(next_socket_fd, next_address);
            CHECK_EXIT(send(next_socket_fd, &token1, sizeof(token1), 0), "Error sending token.\n");

            printf("Token passed\n");
            have_token = 0;
        }

        sleep(1);
    }
}




