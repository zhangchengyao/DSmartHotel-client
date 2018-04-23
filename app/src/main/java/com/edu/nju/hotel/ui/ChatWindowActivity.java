package com.edu.nju.hotel.ui;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.StanzaTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.filetransfer.FileTransfer;
import org.jivesoftware.smackx.filetransfer.FileTransferListener;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;
import org.jivesoftware.smackx.filetransfer.IncomingFileTransfer;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;
import org.jivesoftware.smackx.iqregister.AccountManager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhouchang on 2018/4/15.
 */

public class ChatWindowActivity extends BaseAppCompatActivity  {
    @Override
    protected int getLayoutId() {
        return 0;
    }

    AbstractXMPPConnection connection;
    ChatManager chatManager;
    //    private final String ALBUM_PATH = "./chatPics";
    private final String ALBUM_PATH = "C:\\Users\\Administrator\\Desktop\\chatPics";

    private boolean keepConnection(String confFile) {
        //         Create a connection and login to the example.org XMPP service.
        //         Create the configuration for this new connection
        XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder();
        //        configBuilder.setUsernameAndPassword(username, password);
        configBuilder.setResource("SomeResource");
        configBuilder.setHost("127.0.0.1");
        configBuilder.setPort(5222);
        configBuilder.setServiceName("pc20140911064");
        configBuilder.setSendPresence(true);
        configBuilder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);  //禁用SSL连接
        //        configBuilder.setCustomSSLContext();

        connection = new XMPPTCPConnection(configBuilder.build());
        // Connect to the server
        try {
            connection.connect();
            return true;
        } catch (SmackException | IOException | XMPPException e) {
            e.printStackTrace();
        }
        return false;
    }

    //登录操作是一个网络耗时操作，要放在子线程中去执行
    private boolean login(String username, String password) {
        if (connection==null || !connection.isConnected()) {
            return false;
        }
        try {
            // Log into the server
            connection.login(username, password);
            addTextMsgListener();
            addFriendRequestListener();
            addPicMsgListener();
            return true;
        } catch (SmackException | IOException | XMPPException e) {
            e.printStackTrace();
        }
        return false;
    }

    //注册操作是一个网络耗时操作，要放在子线程中去执行
    private boolean regirster(String username, String password, String name, String email) {
        if (connection==null || !connection.isConnected()) {
            return false;
        }
        try {
            AccountManager.sensitiveOperationOverInsecureConnectionDefault(true);
            Map<String, String> attributes = new HashMap<>();
            attributes.put("email", email);
            attributes.put("name", name);
            AccountManager.getInstance(connection).createAccount(username, password, attributes);
            return true;
        } catch (SmackException.NoResponseException | XMPPException.XMPPErrorException | SmackException.NotConnectedException e) {
            return false;
        }
    }

    private boolean changePassword(String username, String oldPassword, String newPassword) {
        if (connection==null || !connection.isConnected()) {
            return false;
        }
        try {
            if (!connection.isAuthenticated()) {
                connection.login(username, oldPassword);
            }

            AccountManager manager=AccountManager.getInstance(connection);
            manager.sensitiveOperationOverInsecureConnection(true);
            manager.changePassword(newPassword);
            return true;
        } catch (XMPPException | SmackException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void addTextMsgListener() {
        if (connection==null || !connection.isConnected()) {
            return;
        }
        chatManager = ChatManager.getInstanceFor(connection);

        //添加消息接受器
        chatManager.addChatListener(new ChatManagerListener() {
            @Override
            public void chatCreated(Chat chat, boolean b) {
                if (!b) {     //不是本地创建的会话
                    chat.addMessageListener(new ChatMessageListener() {
                        @Override
                        public void processMessage(Chat chat1, Message msg) {
                            System.out.println(msg.getFrom() + ":" + msg.getBody());
                        }
                    });
                }
            }
        });
    }

    private boolean sendTextMsg(String userJID, String msg) {
        if (chatManager==null) {
            return false;
        }
        Chat newChat = chatManager.createChat(userJID, new ChatMessageListener() {
            @Override
            public void processMessage(Chat chat, Message msg) {

            }
        });
        try {
            newChat.sendMessage(msg);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void addPicMsgListener() {
        if (connection==null || !connection.isConnected()) {
            return;
        }
        FileTransferManager manager = FileTransferManager.getInstanceFor(connection);
        manager.addFileTransferListener(new FileTransferListener() {
            @Override
            public void fileTransferRequest(final FileTransferRequest request) {
                new Thread() {
                    @Override
                    public void run() {
                        //文件接收
                        IncomingFileTransfer transfer = request.accept();
                        //获取文件名字
                        String fileName = transfer.getFileName();
                        //本地创建文件
                        File sdCardDir = new File(ALBUM_PATH);
                        if (!sdCardDir.exists()) {//判断文件夹目录是否存在
                            sdCardDir.mkdir();//如果不存在则创建
                        }
                        String save_path = ALBUM_PATH + "/" + fileName;
                        File file = new File(save_path);
                        //接收文件
                        try {
                            transfer.recieveFile(file);
                            while (!transfer.isDone()) {
                                if (transfer.getStatus().equals(FileTransfer.Status.error)) {
                                    System.out.println("ERROR!!! " + transfer.getError());
                                } else {
                                    System.out.println(transfer.getStatus());
                                    System.out.println(transfer.getProgress());
                                }
                                try {
                                    Thread.sleep(1000L);
                                } catch (Exception e) {
                                }
                            }
                            //判断是否完全接收文件
                            if (transfer.isDone()) {
//                                String[] args = new String[]{toUserName, fileName};
//                                android.os.Message msg = handler.obtainMessage();
//                                msg.what = 2;
//                                msg.obj = args;
//                                //发送msg,刷新adapter显示图片
//                                msg.sendToTarget();
                                System.out.println("receive pic successfully");
                            }
                        } catch (SmackException | IOException e) {
                            e.printStackTrace();
                        }
                    };
                }.start();
            }
        });
    }

    private boolean sendPicMsg(String picPath, String receiverJID) {
        if (connection==null || !connection.isConnected()) {
            return false;
        }
        FileTransferManager fileTransferManager = FileTransferManager.getInstanceFor(connection);
        File fileToSend = new File(picPath);
        if (fileToSend.exists() == false) {
            return false;
        }

        Roster roster = Roster.getInstanceFor(connection);
        String fullJID = roster.getPresence(receiverJID).getFrom();
//        System.out.println(fullJID);

        OutgoingFileTransfer transfer = fileTransferManager
                .createOutgoingFileTransfer(fullJID);// 创建一个输出文件传输对象
        try {
            transfer.sendFile(fileToSend, "send img");
            while (!transfer.isDone()) {
                if (transfer.getStatus().equals(FileTransfer.Status.error)) {
                    System.out.println("ERROR!!! " + transfer.getError());
                } else {
                    System.out.println(transfer.getStatus());
                    System.out.println(transfer.getProgress());
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //文件发送完毕
            if (transfer.isDone()) {
//                String[] args = new String[]{mAppGlobal.getName(), img_path};
//                android.os.Message msg = handler.obtainMessage();
//                msg.what = 3;
//                msg.obj = args;
//                //刷新adapter
//                msg.sendToTarget();
                System.out.println("send pic successfully");
                return true;
            }
        } catch ( SmackException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    private boolean sendAddFriendReq(String userJID) {
        if (connection==null || !connection.isConnected()) {
            return false;
        }
        if (!addFriend(userJID, userJID.split("@")[0])) {
            return false;
        }
        //设置添加好友请求
        Presence subscription = new Presence(Presence.Type.subscribe);
        //拼接好友全称
        subscription.setTo(userJID);
        //发送请求
        try {
            connection.sendStanza(subscription);
            return true;
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void addFriendRequestListener() {
        if (connection==null || !connection.isConnected()) {
            return;
        }
        //条件过滤器
        AndFilter filter = new AndFilter(new StanzaTypeFilter(Presence.class));
        //添加监听
        connection.addAsyncStanzaListener(new StanzaListener() {
            @Override
            public void processPacket(Stanza stanza) {
                if (stanza instanceof Presence) {
                    Presence presence = (Presence) stanza;
                    String fromId = presence.getFrom();
                    String from = presence.getFrom().split("@")[0];//我这里只为了打印去掉了后缀

                    System.out.println("receive friend request from " + fromId);

                    addFriend(fromId, from);
                }
            }
        }, filter);
    }

    private boolean addFriend(String friendJID, String friendName) {
        if (connection==null || !connection.isConnected()) {
            return false;
        }
        Roster roster = Roster.getInstanceFor(connection);
        if(roster.contains(friendJID)) {
            return true;
        }
        //添加好友
        try {
            roster.createEntry(friendJID, friendName, null);
            return true;
        } catch (SmackException.NotLoggedInException | SmackException.NoResponseException |
                XMPPException.XMPPErrorException | SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean saveChatHistory2Local(String newMsg, String fileName) {
        return false;
    }

}
