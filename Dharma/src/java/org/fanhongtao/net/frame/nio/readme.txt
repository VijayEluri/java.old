Designs:

There are three kinds of thread: reader thread, writer thread and processor thread. 
1. Reader thread reads message from socket, and puts message into to receiving-list.
2. Processor thread reads messages from receiving-list, processes the messages, and puts response messages, if any, into sending-list.
3. Writer thread reads messages from the sending-list, and writes them into socket.

There are two list: receiving-list and sending-list.
