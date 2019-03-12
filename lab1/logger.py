import socket
import datetime
import time

Port = 19191
IP = "224.0.0.1"

serverSocket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
serverSocket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

serverSocket.bind((IP, Port))
buffer = []

while True:
    buffer = serverSocket.recv(1024)
    log_time = datetime.datetime.fromtimestamp(time.time()).strftime('%Y-%m-%d %H:%M:%S')
    print("logger received: " + buffer + " at time: " + log_time)