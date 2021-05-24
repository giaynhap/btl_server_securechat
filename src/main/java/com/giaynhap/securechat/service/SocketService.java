package com.giaynhap.securechat.service;

import com.giaynhap.securechat.utils.Handler;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class SocketService {
    interface SocketMessageEvent{
        void onMessasge(MessageFilter message);
        void onBlockMessage(MessageFilter message);
        void onTimeoutMessage(MessageFilter message);
    }

    public static  class MessageFilterResponse {
        public String messageId;
        public int length;
        public short numberRevc = 0;
        public byte[] data;
        public long id;
        public int mesageType;

        public boolean isCompletePacket(){
            int chunkSize = SocketService.maxPackageSize - 13;
            int numOfPackage = (int)Math.ceil( (double)length/chunkSize);

            return numOfPackage == numberRevc;
        }

        public MessageFilter readData() throws IOException {
            MessageFilter data = new MessageFilter();
            DataInputStream dataInputStream = new DataInputStream( new ByteArrayInputStream(this.data));
            data.id = id;
            data.messageId = dataInputStream.readUTF();
            data.message  = dataInputStream.readUTF();

            return data;
        }

    }

    public static  class MessageFilter {
        protected String message;
        protected String messageId;
        protected long id;

        public MessageFilter(){
            this.id = SocketService.idGenerator();
        }
        public MessageFilter(String messageId, String message){
            this.id = SocketService.idGenerator();
            this.message = message;
            this.messageId = messageId;
            if (messageId == null) {
                this.messageId = new ObjectId().toHexString();
            }
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getMessageId() {
            return messageId;
        }

        public void setMessageId(String messageId) {
            this.messageId = messageId;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public List<byte[]> getPacket(){
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            try {
                int messageLen = messageId.getBytes(StandardCharsets.UTF_8).length + message.getBytes(StandardCharsets.UTF_8).length;
                // tao goi
                dataOutputStream.writeInt(messageLen  + 4); //message data size + 4
                dataOutputStream.writeUTF(messageId);
                dataOutputStream.writeUTF(message);
                byte[] array =  outputStream.toByteArray();
                dataOutputStream.close();
                outputStream.close();
                // cat goi
                int dataSize =  array.length;
                outputStream = new ByteArrayOutputStream();
                dataOutputStream = new DataOutputStream(outputStream);
                int chunkSize = maxPackageSize - 13;
                ArrayList<byte[]> result = new ArrayList<>();
                // goi du
                if (dataSize <= chunkSize ){
                    dataOutputStream.writeByte(PackageType.FILTER.value); // +1
                    dataOutputStream.writeShort(0);
                    dataOutputStream.writeShort(dataSize); // chunk size
                    dataOutputStream.writeLong(id); // package id
                    dataOutputStream.write(array);
                    result.add(outputStream.toByteArray());
                    dataOutputStream.close();
                    outputStream.close();
                } else {
                    byte[] subData = new byte[chunkSize];
                    int chunkLength = (int)Math.ceil( (double)dataSize/chunkSize);
                    int start = 0;
                    for(int i = 0; i < chunkLength; i++) {
                        int writeChunkSize = chunkSize;
                        if(start + chunkSize < dataSize) {
                            writeChunkSize = chunkSize;
                        } else {
                            writeChunkSize =  dataSize - start;
                        }
                        System.arraycopy(array, start, subData, 0, writeChunkSize);
                        start += writeChunkSize ;

                        outputStream = new ByteArrayOutputStream();
                        dataOutputStream = new DataOutputStream(outputStream);
                        dataOutputStream.writeByte(PackageType.FILTER.value); // +1
                        dataOutputStream.writeShort(i); // packageNumber +2
                        dataOutputStream.writeShort(writeChunkSize); // chunk size +2
                        dataOutputStream.writeLong(id); // package id +8
                        dataOutputStream.write(subData);
                        result.add(outputStream.toByteArray());
                        dataOutputStream.close();
                        outputStream.close();
                    }

                }
                return result;
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
        public List<DatagramPacket> getDatagramPacket(InetAddress address, int port){
            List<byte[]> buffers = getPacket();
            ArrayList<DatagramPacket> result = new ArrayList<>();
            for ( byte[] buffer : buffers) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
                result.add(packet);
            }
            return result;
        }
    }

    class MessageFilterQueue {
        public MessageFilter message;
        public Long queueTime;
        public int expired;

        public MessageFilterQueue(MessageFilter message,int expired) {
            this.message = message;
            this.queueTime =  System.currentTimeMillis();
            this.expired = expired;
        }

        public Boolean isExpired(){
            Long current = System.currentTimeMillis();
            if (current - this.queueTime >= this.expired){
                return true;
            }
            return false;
        }
    }
    LinkedBlockingQueue<MessageFilterQueue> queues = new LinkedBlockingQueue<>();
    LinkedBlockingQueue<MessageFilterQueue> sendingQueue = new LinkedBlockingQueue<>();

    protected final Logger LOG ;
    enum PackageType  {
        ACK((byte)0),
        FILTER((byte)1),
        BLOCK((byte)2);
        public byte value;
        PackageType(byte value){
            this.value = value;
        }
    }

    Thread outputThread;
    Thread inputThread;
    InetAddress address;
    DatagramSocket socket;
    long lastTimeACK = 0;
    static int port = 9191;
    static String host = "127.0.0.1";
    static int maxPackageSize = 1460;
    boolean isRunning = true;
    boolean serverConnected = false;
    HashMap<Long, MessageFilterResponse> packetCache =  new HashMap<Long, MessageFilterResponse>();
    Handler timeoutHandler = new Handler();
    int pingTime = 2000;
    int lostPingTime = 3000;
    byte pingCount = 0;
    byte numMessagePerTime = 10;
    private SocketMessageEvent listener = null;

    public SocketMessageEvent getListener() {
        return listener;
    }

    public void setListener(SocketMessageEvent listener) {
        this.listener = listener;
    }

    public static long idGenerator() {
        return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }


    public SocketService(){
        this.init();
        LOG = LoggerFactory.getLogger(getClass());
        
      /*  MessageFilter filter = new MessageFilter();
        filter.id = System.currentTimeMillis();
        filter.messageId = "đỗ văn thực muối nước lọc";
        filter.message ="lớp ct1a";
        this.sendMessage( filter );*/
        LOG.info("Initializing..");

        try {
            pingServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void pingServer() throws IOException {
        if (lastTimeACK == 0){
            lastTimeACK = System.currentTimeMillis();
        }

        long current = System.currentTimeMillis();
        if (current - lastTimeACK >= pingTime){
            LOG.warn("server ping timeout");
            serverConnected = false;
            checkQueueItemsTimeout();
        }
        //LOG.info("Send ACK message");
        lastTimeACK = current;
        pingCount += 1;

        if (pingCount > 5) {
            pingCount = 0;
        }
        byte buffer[] = {PackageType.ACK.value,pingCount};
        DatagramPacket pingPacket = new DatagramPacket(buffer, buffer.length, address, port);
        socket.send(pingPacket);
        checkSendingTimeout();
        timeoutHandler.postDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    pingServer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        },serverConnected ? pingTime : lostPingTime);
    }

    public void init(){

        try {
            address = InetAddress.getByName(host);
            socket = new DatagramSocket();
        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }

        outputThread = new Thread(outputRunner, "SocketOutput");
        inputThread = new Thread(inputRunner, "SocketIntput");
        inputThread.start();
        outputThread.start();
    }

    public void sendMessageQueue(MessageFilter messageFilter){
        MessageFilterQueue entry = new MessageFilterQueue(messageFilter, 5000);
        queues.add(entry);
    }

    public void sendMessage(MessageFilter message){

            try {
                //LOG.warn("Send message id "+message.id);
                List<DatagramPacket> pakets = message.getDatagramPacket(address, port);
                for (DatagramPacket paket : pakets) {
                    socket.send(paket);
                }

            } catch (IOException e) {
                e.printStackTrace();
        }
    }

    private void checkQueueItemsTimeout() {
        if (!serverConnected) {
            if (queues.size() > 0) {
                queues.forEach(m -> {
                    if (m.isExpired()) {
                        if (SocketService.this.listener != null){
                            SocketService.this.listener.onTimeoutMessage(m.message);
                         }
                        queues.remove(m);
                    }
                });
            }

        }
    }

    private  void checkSendingTimeout() {
        if (sendingQueue.size() > 0 ) {
            sendingQueue.forEach(m -> {
                if (m.isExpired()) {
                    if (SocketService.this.listener != null) {
                        SocketService.this.listener.onTimeoutMessage(m.message);
                    }
                    sendingQueue.remove(m);
                }
            });
        }
    }

    private Runnable outputRunner = new Runnable() {

        private void safeSleep(int time){
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void run() {
            if (address == null){
                return;
            }

            while (Thread.currentThread() == outputThread){

                if (!serverConnected) {
                    safeSleep(200);
                    continue;
                }

                if (queues.size() > 0 && sendingQueue.size() < numMessagePerTime) {
                    int canRevc = numMessagePerTime - sendingQueue.size();
                    if (canRevc > queues.size()) {
                        canRevc = queues.size();
                    }
                    while (canRevc > 0) {
                        try {
                            MessageFilterQueue item = null;
                            item = queues.take();
                            if (item.isExpired()) {
                                if ( SocketService.this.listener != null) {
                                    SocketService.this.listener.onTimeoutMessage(item.message);
                                }
                                continue;
                            }

                            sendMessage(item.message);
                            item.queueTime = System.currentTimeMillis();
                            sendingQueue.put(item);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        canRevc--;
                    }
                }

                safeSleep(10);
            }

        }
    };



    private Runnable inputRunner = new Runnable() {
        @Override
        public void run() {
            if (address == null){
                return;
            }

            while (Thread.currentThread() == inputThread){
                byte[] buffer = new byte[SocketService.maxPackageSize];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                try {
                    socket.receive(packet);
                    MessageFilterResponse response = handlePackage(packet);

                    if (response == null){
                        continue;
                    }
                    MessageFilter m = response.readData();
                    if (response.mesageType == PackageType.BLOCK.value) {
                        LOG.info("Recv block message " + m.messageId+ "  - Reason "+ m.message );

                        if (SocketService.this.listener != null) {
                            SocketService.this.listener.onBlockMessage(m);
                        }

                    } else {

                        // LOG.info("test " + m.message +"  "+ m.messageId  );
                        boolean validateMessage = false;
                        MessageFilterQueue existMessage = null;

                        for (MessageFilterQueue mq : sendingQueue) {
                            //LOG.info("check timeout message " + mq.message.id + "   " + (mq.message.id == m.id ));
                            if (mq.message.id == m.id) {
                                validateMessage = true;
                                existMessage = mq;
                                break;
                            }
                        }

                        if (validateMessage) {
                            sendingQueue.remove(existMessage);
                            if (SocketService.this.listener != null) {
                                SocketService.this.listener.onMessasge(m);
                            }
                        } else {
                            LOG.warn("Recv timeout message " + m.id);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private  MessageFilterResponse handlePackage(DatagramPacket packet) throws IOException {

        DataInputStream dataInputStream = new DataInputStream( new ByteArrayInputStream(packet.getData()));
        byte type = dataInputStream.readByte();
        //LOG.info("Revc package Packet " + type);
        if (type == PackageType.ACK.value) {
            byte count =  dataInputStream.readByte();
            lastTimeACK = 0;
            serverConnected = true;
            //LOG.info("Revc ACK Packet ("+count+")");
        } else if (type == PackageType.FILTER.value || type == PackageType.BLOCK.value) {
            if (packet.getLength() < 20){
                return null;
            }
            short packetNumber = dataInputStream.readShort();
            short chunkSize = dataInputStream.readShort();
            long packetId = dataInputStream.readLong();
            int chunkMaxSize = SocketService.maxPackageSize - 13;
            MessageFilterResponse response = packetCache.get(packetId);

            /*LOG.info(" chunk size: " + chunkSize);
            LOG.info(" packetNumber: " + packetNumber);
            LOG.info(" packetId: " + packetId);*/
            if (response == null) {
                response = new MessageFilterResponse();
                response.data = new byte[maxPackageSize * (packetNumber + 1)];
                response.id = packetId;
            }

            if (packetNumber == 0) {
                int packetLength = dataInputStream.readInt();
                response.length = packetLength;
                response.data = Arrays.copyOf(response.data, response.length);
            }

            response.numberRevc += 1;
            int offset = (chunkMaxSize * packetNumber > 0) ? (chunkMaxSize * packetNumber - 4) : (chunkMaxSize * packetNumber);
            int length = (chunkMaxSize * packetNumber == 0) ? (chunkSize - 4) : (chunkSize);
            dataInputStream.read(response.data, offset, length);
            packetCache.put(packetId, response);


            if (response.isCompletePacket()) {
                response.mesageType = type;
                packetCache.remove(packetId);
                return response;
            }
        }

        return null;
    }


}
