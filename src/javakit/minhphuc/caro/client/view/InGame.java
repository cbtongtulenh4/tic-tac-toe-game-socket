
package javakit.minhphuc.caro.client.view;


import javakit.minhphuc.caro.client.RunClient;
import javakit.minhphuc.dto.CustomRenderer;
import javakit.minhphuc.dto.PlayerDTO;
import javakit.minhphuc.dto.PlayerInGameDTO;
import javakit.minhphuc.dto.ServerChatDTO;
import javakit.minhphuc.util.Avatar;
import javakit.minhphuc.util.CountDownTimer;
import javakit.minhphuc.util.TimeUtils;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.concurrent.Callable;


public class InGame extends JFrame {

    final ImageIcon p1Icon = new ImageIcon(Avatar.ASSET_PATH + "icons8_round_24px.png");
    final ImageIcon p2Icon = new ImageIcon(Avatar.ASSET_PATH + "icons8_delete_24px_1.png");

    // https://codelearn.io/sharing/lam-game-caro-don-gian-bang-java
    final int COLUMN = 16, ROW = 16;

    DefaultListModel<PlayerInGameDTO> listPLayersModel;
    PlayerInGameDTO player1;
    PlayerInGameDTO player2;
    int turn = 0;

    JButton btnOnBoard[][];
    JButton lastMove = null;

    CountDownTimer turnTimer;
    CountDownTimer matchTimer;

    /**
     * Creates new form InGame
     */
    public InGame() {
        initComponents();
        this.setLocationRelativeTo(null);

        // list user
        listPLayersModel = new DefaultListModel<>();
        lListUser.setModel(listPLayersModel);
        lListUser.setCellRenderer(new CustomRenderer());
        // https://stackoverflow.com/a/4344762
        lListUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList) evt.getSource();
                if (evt.getClickCount() == 2) {
                    // Double-click detected
                    int index = list.locationToIndex(evt.getPoint());

                    RunClient.openScene(RunClient.Scene.PROFILE);
                    RunClient.profileScene.loadProfileData(listPLayersModel.get(index).getEmail());
                }
            }
        });

        // board
        plBoardContainer.setLayout(new GridLayout(ROW, COLUMN));
        initBoard();

        // https://stackoverflow.com/a/1627068
        ((DefaultCaret) txaChat.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        // close window event
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(InGame.this,
                        "Bạn có chắc muốn thoát phòng?", "Thoát phòng?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
//                    RunClient.clientController.leaveRoom();
                }
            }
        });
    }

    public void setPlayerInGame(PlayerInGameDTO p1, PlayerInGameDTO p2) {
        // save data
        player1 = p1;
        player2 = p2;

        // player 1
        lbPlayerNameId1.setText(p1.getName());
        if (p1.getAvatar().equals("")) {
            lbAvatar1.setIcon(new ImageIcon(Avatar.PATH + Avatar.EMPTY_AVATAR));
        } else {
            lbAvatar1.setIcon(new ImageIcon(Avatar.PATH + p1.getAvatar()));
        }

        // player 2
        lbPlayerNameId2.setText(p2.getName());
        lbAvatar2.setIcon(new ImageIcon(Avatar.PATH + Avatar.EMPTY_AVATAR));
        if (p2.getAvatar().equals("")) {
            lbAvatar2.setIcon(new ImageIcon(Avatar.PATH + Avatar.EMPTY_AVATAR));
        } else {
            lbAvatar2.setIcon(new ImageIcon(Avatar.PATH + p2.getAvatar()));
        }

        // reset turn
        lbActive1.setVisible(false);
        lbActive2.setVisible(false);
    }

    public void setListUser(ArrayList<PlayerInGameDTO> list) {
        listPLayersModel.clear();

        for (PlayerInGameDTO p : list) {
            listPLayersModel.addElement(p);
        }
    }

    public void setWin(PlayerDTO player) {
        // pause timer
        matchTimer.pause();
        turnTimer.pause();

        // tie
        if (player.getEmail() == null) {
            addChat(new ServerChatDTO(TimeUtils.currentTime(), "KẾT QUẢ", "HÒA"));
            JOptionPane.showMessageDialog(this, "Trận đấu kết thúc với tỉ số HÒA.", "HÒA", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // get myEmail
        String myEmail = RunClient.clientController.getPlayer().getEmail();

        if (player.getEmail().equals(myEmail)) {
            // là email của mình thì win
            addChat(new ServerChatDTO(TimeUtils.currentTime(), "KẾT QUẢ", "Bạn đã thắng"));
            JOptionPane.showMessageDialog(this, "Chúc mừng. Bạn đã chiến thắng.", "Chiến thắng", JOptionPane.INFORMATION_MESSAGE);

        } else if (myEmail.equals(player1.getEmail()) || myEmail.equals(player2.getEmail())) {
            // nếu mình là 1 trong 2 người chơi, mà winEmail ko phải mình => thua
           addChat(new ServerChatDTO(TimeUtils.currentTime(), "KẾT QUẢ", "Bạn đã thua"));
            JOptionPane.showMessageDialog(this, "Rất tiếc. Bạn đã thua cuộc.", "Thua cuộc", JOptionPane.INFORMATION_MESSAGE);

        } else {
            // còn lại là viewers
            String nameId = "";
            if (player1.getEmail().equals(player.getEmail())) {
                nameId = player1.getName();
            } else {
                nameId = player2.getName();
            }
            addChat(new ServerChatDTO(TimeUtils.currentTime(), "KẾT QUẢ", "Người chơi " + nameId + " đã thắng"));
            JOptionPane.showMessageDialog(this, "Người chơi " + nameId + " đã thắng", "Kết quả", JOptionPane.INFORMATION_MESSAGE);
        }

        // thoát phòng sau khi thua 
        // TODO sau này sẽ cho tạo ván mới, hiện tại cho thoát để tránh lỗi
        // RunClient.clientController.leaveRoom();
    }

    public void startGame(int turnTimeLimit, int matchTimeLimit) {
        turnTimer = new CountDownTimer(turnTimeLimit);
        turnTimer.setTimerCallBack(
                // end match callback
                null,
                // tick match callback
                (Callable) () -> {
                    pgbTurnTimer.setValue(100 * turnTimer.getCurrentTick() / turnTimer.getTimeLimit());
                    pgbTurnTimer.setString(TimeUtils.secondsToMinutes(turnTimer.getCurrentTick()));
                    return null;
                },
                // tick interval
                1
        );

        matchTimer = new CountDownTimer(matchTimeLimit);
        matchTimer.setTimerCallBack(
                // end match callback
                null,
                // tick match callback
                (Callable) () -> {
                    pgbMatchTimer.setValue(100 * matchTimer.getCurrentTick() / matchTimer.getTimeLimit());
                    pgbMatchTimer.setString("" + TimeUtils.secondsToMinutes(matchTimer.getCurrentTick()));
                    return null;
                },
                // tick interval
                1
        );
    }

    public void setTurnTimerTick(int value) {
        turnTimer.setCurrentTick(value);
    }

    public void setMatchTimerTick(int value) {
        matchTimer.setCurrentTick(value);
    }

    // change turn sang cho email đầu vào
    public void setTurn(String email) {
        if (player1.getEmail().equals(email)) {
            turnTimer.restart();
            turn = 1;
            lbActive1.setVisible(true);
            lbActive2.setVisible(false);
            lbAvatar1.setBorder(BorderFactory.createTitledBorder("Đang đánh.."));
            lbAvatar2.setBorder(BorderFactory.createTitledBorder("Chờ"));
        }

        if (player2.getEmail().equals(email)) {
            turnTimer.restart();
            turn = 2;
            lbActive1.setVisible(false);
            lbActive2.setVisible(true);
            lbAvatar1.setBorder(BorderFactory.createTitledBorder("Chờ"));
            lbAvatar2.setBorder(BorderFactory.createTitledBorder("Đang đánh.."));
        }
    }

    // change turn sang cho đối thủ của email đầu vào
    public void changeTurnFrom(String email) {
        if (email.equals(player1.getEmail())) {
            setTurn(player2.getEmail());
        } else {
            setTurn(player1.getEmail());
        }
    }

    public void initBoard() {
        plBoardContainer.removeAll();
        btnOnBoard = new JButton[COLUMN + 2][ROW + 2];

        for (int row = 0; row < ROW; row++) {
            for (int column = 0; column < COLUMN; column++) {
                btnOnBoard[row][column] = createBoarDButton(row, column);
                plBoardContainer.add(btnOnBoard[row][column]);
            }
        }
    }

    public void setLastMove(int row, int column) {
        lastMove = btnOnBoard[row][column];
    }

    public void addPoint(int row, int column, String email) {
        if (lastMove != null) {
            lastMove.setBackground(new Color(180, 180, 180));
        }

        lastMove = btnOnBoard[row][column];
        lastMove.setBackground(Color.yellow);
        lastMove.setActionCommand(email); // save email as state

        if (email.equals(player1 != null ? player1.getEmail() : "")) {
            lastMove.setIcon(p1Icon);
        } else {
            lastMove.setIcon(p2Icon);
        }
    }

    public void removePoint(int row, int column) {
        https://stackoverflow.com/a/2235596
        btnOnBoard[row][column].setIcon(null);
        btnOnBoard[row][column].setActionCommand("");
    }

    public void clickOnBoard(int row, int column) {
        String myEmail = RunClient.clientController.getPlayer().getEmail();

        if (myEmail.equals(player1.getEmail()) || myEmail.equals(player2.getEmail())) {
            RunClient.clientController.move(row, column);
        }
    }

    public JButton createBoarDButton(int row, int column) {
        JButton b = new JButton();
        b.setFocusPainted(false);
        b.setBackground(new Color(180, 180, 180));
        b.setActionCommand("");

        b.addActionListener((ActionEvent e) -> {
            clickOnBoard(row, column);

            // test
            // addPoint(row, column, "");
        });

        // https://stackoverflow.com/a/22639054
        b.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                if (b.getActionCommand().equals("")) {

                    String myEmail = RunClient.clientController.getPlayer().getEmail();

                    if (myEmail.equals(player1.getEmail()) && (turn == 1 || turn == 0)) {
                        b.setIcon(p1Icon);
                    }

                    if (myEmail.equals(player2.getEmail()) && (turn == 2 || turn == 0)) {
                        b.setIcon(p2Icon);
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                if (b.getActionCommand().equals("")) {
                    b.setIcon(null);
                }
            }
        });

        return b;
    }

    public void addChat(Object chat) {
        txaChat.append(chat.toString() + "\n");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        plRightContainer = new JPanel();
        plToolContainer = new JPanel();
        btnNewGame = new JButton();
        btnUndo = new JButton();
        btnLeaveRoom = new JButton();
        plPlayerContainer = new JPanel();
        plPlayer = new JPanel();
        lbAvatar1 = new JLabel();
        lbPlayerNameId1 = new JLabel();
        lbActive1 = new JLabel();
        lbVersus = new JLabel();
        lbAvatar2 = new JLabel();
        lbPlayerNameId2 = new JLabel();
        lbActive2 = new JLabel();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        plTimer = new JPanel();
        jLabel4 = new JLabel();
        jLabel5 = new JLabel();
        pgbTurnTimer = new JProgressBar();
        pgbMatchTimer = new JProgressBar();
        tpChatAndViewerContainer = new JTabbedPane();
        jPanel3 = new JPanel();
        jPanel7 = new JPanel();
        txChatInput = new JTextField();
        btnSendMessage = new JButton();
        jScrollPane3 = new JScrollPane();
        txaChat = new JTextArea();
        jPanel4 = new JPanel();
        jScrollPane2 = new JScrollPane();
        lListUser = new JList<>();
        plBoardContainer = new JPanel();

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Caro Game");
        setResizable(false);

        plToolContainer.setBorder(BorderFactory.createTitledBorder("Chức năng"));

        btnNewGame.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnNewGame.setIcon(new ImageIcon(getClass().getResource("/javakit/minhphuc/caro/client/view/asset/icons8_new_file_24px.png"))); // NOI18N
        btnNewGame.setText("Ván mới");

        btnUndo.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnUndo.setIcon(new ImageIcon(getClass().getResource("/javakit/minhphuc/caro/client/view/asset/icons8_undo_24px.png"))); // NOI18N
        btnUndo.setText("Đánh lại");

        btnLeaveRoom.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnLeaveRoom.setIcon(new ImageIcon(getClass().getResource("/javakit/minhphuc/caro/client/view/asset/icons8_exit_sign_24px.png"))); // NOI18N
        btnLeaveRoom.setText("Thoát phòng");
        btnLeaveRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnLeaveRoomActionPerformed(evt);
            }
        });

        GroupLayout plToolContainerLayout = new GroupLayout(plToolContainer);
        plToolContainer.setLayout(plToolContainerLayout);
        plToolContainerLayout.setHorizontalGroup(
            plToolContainerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, plToolContainerLayout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(plToolContainerLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addGroup(plToolContainerLayout.createSequentialGroup()
                        .addComponent(btnNewGame)
                        .addGap(6, 6, 6)
                        .addComponent(btnUndo))
                    .addComponent(btnLeaveRoom))
                .addGap(42, 42, 42))
        );
        plToolContainerLayout.setVerticalGroup(
            plToolContainerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, plToolContainerLayout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(plToolContainerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNewGame)
                    .addComponent(btnUndo))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLeaveRoom)
                .addGap(18, 18, 18))
        );

        plPlayer.setBorder(BorderFactory.createTitledBorder("Người chơi"));

        lbAvatar1.setHorizontalAlignment(SwingConstants.CENTER);
        lbAvatar1.setIcon(new ImageIcon(getClass().getResource("/javakit/minhphuc/caro/client/view/asset/avatar/icons8_circled_user_male_skin_type_7_96px.png"))); // NOI18N
        lbAvatar1.setBorder(BorderFactory.createTitledBorder("..."));
        lbAvatar1.setOpaque(true);

        lbPlayerNameId1.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        lbPlayerNameId1.setHorizontalAlignment(SwingConstants.CENTER);
        lbPlayerNameId1.setText("Player1");

        lbActive1.setIcon(new ImageIcon(getClass().getResource("/javakit/minhphuc/caro/client/view/asset/icons8_sphere_30px.png"))); // NOI18N

        lbVersus.setHorizontalAlignment(SwingConstants.CENTER);
        lbVersus.setIcon(new ImageIcon(getClass().getResource("/javakit/minhphuc/caro/client/view/asset/icons8_sword_48px.png"))); // NOI18N

        lbAvatar2.setHorizontalAlignment(SwingConstants.CENTER);
        lbAvatar2.setIcon(new ImageIcon(getClass().getResource("/javakit/minhphuc/caro/client/view/asset/avatar/icons8_circled_user_female_skin_type_7_96px.png"))); // NOI18N
        lbAvatar2.setBorder(BorderFactory.createTitledBorder("..."));

        lbPlayerNameId2.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        lbPlayerNameId2.setHorizontalAlignment(SwingConstants.CENTER);
        lbPlayerNameId2.setText("Player2");

        lbActive2.setIcon(new ImageIcon(getClass().getResource("/javakit/minhphuc/caro/client/view/asset/icons8_sphere_30px.png"))); // NOI18N

        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setIcon(new ImageIcon(getClass().getResource("/javakit/minhphuc/caro/client/view/asset/icons8_round_24px.png"))); // NOI18N

        jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel2.setIcon(new ImageIcon(getClass().getResource("/javakit/minhphuc/caro/client/view/asset/icons8_delete_24px_1.png"))); // NOI18N

        GroupLayout plPlayerLayout = new GroupLayout(plPlayer);
        plPlayer.setLayout(plPlayerLayout);
        plPlayerLayout.setHorizontalGroup(
            plPlayerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(plPlayerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(plPlayerLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbAvatar1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbPlayerNameId1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(plPlayerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lbVersus, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(plPlayerLayout.createSequentialGroup()
                        .addComponent(lbActive1)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbActive2)))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(plPlayerLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbAvatar2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbPlayerNameId2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        plPlayerLayout.setVerticalGroup(
            plPlayerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(plPlayerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(plPlayerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(plPlayerLayout.createSequentialGroup()
                        .addGroup(plPlayerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(plPlayerLayout.createSequentialGroup()
                                .addComponent(lbAvatar1)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbPlayerNameId1, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
                            .addGroup(plPlayerLayout.createSequentialGroup()
                                .addComponent(lbAvatar2)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbPlayerNameId2, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(plPlayerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                            .addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(plPlayerLayout.createSequentialGroup()
                        .addGroup(plPlayerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(lbVersus, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
                            .addGroup(plPlayerLayout.createSequentialGroup()
                                .addGap(125, 125, 125)
                                .addGroup(plPlayerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(lbActive1)
                                    .addComponent(lbActive2))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        plTimer.setBorder(BorderFactory.createTitledBorder("Thời gian"));

        jLabel4.setText("Nước đi");

        jLabel5.setText("Trận đấu");

        pgbTurnTimer.setValue(100);
        pgbTurnTimer.setString("Đang đợi nước đi đầu tiên..");
        pgbTurnTimer.setStringPainted(true);

        pgbMatchTimer.setValue(100);
        pgbMatchTimer.setString("Đang đợi nước đi đầu tiên..");
        pgbMatchTimer.setStringPainted(true);

        GroupLayout plTimerLayout = new GroupLayout(plTimer);
        plTimer.setLayout(plTimerLayout);
        plTimerLayout.setHorizontalGroup(
            plTimerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(plTimerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(plTimerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(plTimerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(pgbTurnTimer, GroupLayout.PREFERRED_SIZE, 252, GroupLayout.PREFERRED_SIZE)
                    .addComponent(pgbMatchTimer, GroupLayout.PREFERRED_SIZE, 252, GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        plTimerLayout.setVerticalGroup(
            plTimerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(plTimerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(plTimerLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pgbTurnTimer, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(plTimerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pgbMatchTimer, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        GroupLayout plPlayerContainerLayout = new GroupLayout(plPlayerContainer);
        plPlayerContainer.setLayout(plPlayerContainerLayout);
        plPlayerContainerLayout.setHorizontalGroup(
            plPlayerContainerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(plPlayer, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(plTimer, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        plPlayerContainerLayout.setVerticalGroup(
            plPlayerContainerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(plPlayerContainerLayout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(plPlayer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(plTimer, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txChatInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                txChatInputKeyPressed(evt);
            }
        });

        btnSendMessage.setIcon(new ImageIcon(getClass().getResource("/javakit/minhphuc/caro/client/view/asset/icons8_paper_plane_24px.png"))); // NOI18N
        btnSendMessage.setText("Gửi");
        btnSendMessage.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                btnSendMessageMouseClicked(evt);
            }
        });

        GroupLayout jPanel7Layout = new GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txChatInput, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSendMessage, GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(btnSendMessage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txChatInput))
                .addContainerGap())
        );

        txaChat.setEditable(false);
        txaChat.setColumns(20);
        txaChat.setRows(5);
        jScrollPane3.setViewportView(txaChat);

        GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        tpChatAndViewerContainer.addTab("Nhắn tin", jPanel3);

        jScrollPane2.setViewportView(lListUser);

        GroupLayout jPanel4Layout = new GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                .addContainerGap())
        );

        tpChatAndViewerContainer.addTab("Người trong phòng", jPanel4);

        GroupLayout plRightContainerLayout = new GroupLayout(plRightContainer);
        plRightContainer.setLayout(plRightContainerLayout);
        plRightContainerLayout.setHorizontalGroup(
            plRightContainerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(plPlayerContainer, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(plToolContainer, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tpChatAndViewerContainer)
        );
        plRightContainerLayout.setVerticalGroup(
            plRightContainerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, plRightContainerLayout.createSequentialGroup()
                .addComponent(plToolContainer, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(plPlayerContainer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tpChatAndViewerContainer, GroupLayout.PREFERRED_SIZE, 257, GroupLayout.PREFERRED_SIZE))
        );

        plBoardContainer.setLayout(new GridLayout(1, 0));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(plBoardContainer, GroupLayout.PREFERRED_SIZE, 700, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(plRightContainer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(plBoardContainer, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(plRightContainer, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSendMessageMouseClicked(MouseEvent evt) {//GEN-FIRST:event_btnSendMessageMouseClicked
        String chatMsg = txChatInput.getText();
        txChatInput.setText("");

        if (!chatMsg.equals("")) {
            RunClient.clientController.chatRoom(chatMsg);
        }
    }//GEN-LAST:event_btnSendMessageMouseClicked

    private void txChatInputKeyPressed(KeyEvent evt) {//GEN-FIRST:event_txChatInputKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnSendMessageMouseClicked(null);
        }
    }//GEN-LAST:event_txChatInputKeyPressed

    private void btnLeaveRoomActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnLeaveRoomActionPerformed
        // https://stackoverflow.com/a/8689130
        if (JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn thoát phòng?", "Warning",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            RunClient.clientController.leaveRoom();
        }
    }//GEN-LAST:event_btnLeaveRoomActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InGame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InGame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InGame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InGame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InGame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnLeaveRoom;
    private JButton btnNewGame;
    private JButton btnSendMessage;
    private JButton btnUndo;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel7;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JList<PlayerInGameDTO> lListUser;
    private JLabel lbActive1;
    private JLabel lbActive2;
    private JLabel lbAvatar1;
    private JLabel lbAvatar2;
    private JLabel lbPlayerNameId1;
    private JLabel lbPlayerNameId2;
    private JLabel lbVersus;
    private JProgressBar pgbMatchTimer;
    private JProgressBar pgbTurnTimer;
    private JPanel plBoardContainer;
    private JPanel plPlayer;
    private JPanel plPlayerContainer;
    private JPanel plRightContainer;
    private JPanel plTimer;
    private JPanel plToolContainer;
    private JTabbedPane tpChatAndViewerContainer;
    private JTextField txChatInput;
    private JTextArea txaChat;
    // End of variables declaration//GEN-END:variables
}
