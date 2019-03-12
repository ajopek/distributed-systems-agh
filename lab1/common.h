//
// Created by artur on 09.03.19.
//

#ifndef LAB1_COMMON_H
#define LAB1_COMMON_H

#include <string.h>
#include <errno.h>
#include <stdlib.h>
#include <stdio.h>
#include <sys/socket.h>
#include <sys/un.h>
#include <sys/types.h>
#include <netinet/in.h>

#define CHECK_EXIT(_EXPR, _ERR_MSG) if((_EXPR) < 0) { fprintf(stderr, "%s: %d %s\n", _ERR_MSG, errno, strerror(errno)); exit(1); }
#define EXIT_ERR(_ERR_MSG) {fprintf(stderr, "%s: %d %s\n", _ERR_MSG, errno, strerror(errno)); exit(1)};
#define CHECK(_EXPR, _ERR_MSG) if((_EXPR) < 0) { fprintf(stderr, "%s: %d %s\n", _ERR_MSG, errno, strerror(errno)); }

#define MAX_NAME_LEN 30
#define MAX_LEN 200

#define LOGGER_PORT 19191

typedef enum
{
    NEW_CLIENT = 0x11,
    MESSAGE = 0x22
} msg_type;

typedef struct
{
    uint32_t len;
    char content[100];
} message;

typedef struct
{
    msg_type type;
    int free;
    char from[MAX_NAME_LEN];
    char to[MAX_NAME_LEN];
    struct sockaddr_in from_addr;
    struct sockaddr_in new_conn_addr;
    message msg;
    int port;
} token;

typedef struct
{

} client_state;

#endif //LAB1_COMMON_H
