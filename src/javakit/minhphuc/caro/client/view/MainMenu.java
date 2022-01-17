
package javakit.minhphuc.caro.client.view;


import javakit.minhphuc.caro.client.RunClient;
import javakit.minhphuc.util.SystemConstant;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;


public class MainMenu extends javax.swing.JFrame {

    public enum State {
        DEFAULT,
        FINDING_MATCH,
        WAITING_ACCEPT,
        WAITING_COMPETITOR_ACCEPT
    };
//
//    CountDownTimer acceptPairMatchTimer;
//    CountDownTimer waitingPairTimer;
//    final int acceptWaitingTime = 15;
//
//    boolean pairAcceptChoosed = false;

    /**
     * Creates new form MainMenuF
     */
    public MainMenu() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setTitle(SystemConstant.ARENA_HALL + " - " + RunClient.clientController.getPlayer().getEmail());

        // default to hidden
        setDisplayState(State.DEFAULT);
    }

    public void setListChamber(Vector vdata, Vector vheader) {
        tbListChamber.setModel(new DefaultTableModel(vdata, vheader));
    }

    public void foundMatch(String competitorName) {
        setDisplayState(State.WAITING_ACCEPT);
        lbFoundMatch.setText("Đã tìm thấy đối thủ " + competitorName + " . Vào ngay?");
    }

//    private void stopAcceptPairMatchTimer() {
//        if (acceptPairMatchTimer != null) {
//            acceptPairMatchTimer.cancel();
//        }
//    }

//    private void startAcceptPairMatchTimer() {
//        acceptPairMatchTimer = new CountDownTimer(acceptWaitingTime);
//        acceptPairMatchTimer.setTimerCallBack(
//                // end callback
//                (Callable) () -> {
//                    // reset acceptPairMatchTimer
//                    acceptPairMatchTimer.restart();
//                    acceptPairMatchTimer.pause();
//
//                    // tự động từ chối nếu quá thời gian mà chưa chọn đồng ý
//                    if (!pairAcceptChoosed) {
//                        RunClient.clientController.declinePairMatch();
//                    }
//                    return null;
//                },
//                // tick callback
//                (Callable) () -> {
//                    lbTimerPairMatch.setText(acceptPairMatchTimer.getCurrentTick() + "s");
//                    return null;
//                },
//                // tick interval
//                1
//        );
//    }

//    private void stopWaitingPairMatchTimer() {
//        if (waitingPairTimer != null) {
//            waitingPairTimer.cancel();
//        }
//    }

//    private void startWaitingPairMatchTimer() {
//        waitingPairTimer = new CountDownTimer(5 * 60); // 5p
//        waitingPairTimer.setTimerCallBack(
//                (Callable) () -> {
//                    setDisplayState(State.DEFAULT);
//                    JOptionPane.showMessageDialog(this, "Mãi chả thấy ai tìm trận.. Xui");
//                    return null;
//                },
//                (Callable) () -> {
//                    lbFindMatch.setText("Đang tìm trận.. " + (5 * 60 - waitingPairTimer.getCurrentTick()) + "s");
//                    return null;
//                },
//                1
//        );
//    }

    public void setDisplayState(State s) {

        // mở hết lên
//        LookAndFeel.enableComponents(plBtns, true);
        plFoundMatch.setVisible(true);
        plFindingMatch.setVisible(true);
        btnAcceptPairMatch.setEnabled(true);
        btnDeclinePairMatch.setEnabled(true);
        btnLogout.setEnabled(true);

        // xong đóng từng cái tùy theo state
        switch (s) {
            case DEFAULT:
//                stopWaitingPairMatchTimer();
//                stopAcceptPairMatchTimer();
                plFindingMatch.setVisible(false);
                plFoundMatch.setVisible(false);
                break;

            case FINDING_MATCH:
//                startWaitingPairMatchTimer();
//                stopAcceptPairMatchTimer();
//                LookAndFeel.enableComponents(plBtns, false);
                plFoundMatch.setVisible(false);
                btnLogout.setEnabled(false);
                break;

            case WAITING_ACCEPT:
//                stopWaitingPairMatchTimer();
//                startAcceptPairMatchTimer();
//                pairAcceptChoosed = false;
//                LookAndFeel.enableComponents(plBtns, false);
                plFindingMatch.setVisible(false);
                btnLogout.setEnabled(false);
                break;

            case WAITING_COMPETITOR_ACCEPT:
//                LookAndFeel.enableComponents(plBtns, false);
//                pairAcceptChoosed = true;
                plFindingMatch.setVisible(false);
                btnAcceptPairMatch.setEnabled(false);
                btnDeclinePairMatch.setEnabled(false);
                btnLogout.setEnabled(false);
                lbFoundMatch.setText("Đang chờ đối thủ..");
                break;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel10 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        plBtns = new javax.swing.JPanel();
        btnCreateChamber = new javax.swing.JButton();
        btnFindMatch = new javax.swing.JButton();
        btnJoin = new javax.swing.JButton();
        btnWatch = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnLogout = new javax.swing.JButton();
        btnProfile = new javax.swing.JButton();
        plFindingMatch = new javax.swing.JPanel();
        jProgressBar1 = new javax.swing.JProgressBar();
        lbFindMatch = new javax.swing.JLabel();
        jProgressBar2 = new javax.swing.JProgressBar();
        btnCancelFindMatch = new javax.swing.JButton();
        tpChamberAndUser = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbListChamber = new javax.swing.JTable();
        btnRefreshListChamber = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();
        plFoundMatch = new javax.swing.JPanel();
        lbFoundMatch = new javax.swing.JLabel();
        btnDeclinePairMatch = new javax.swing.JButton();
        btnAcceptPairMatch = new javax.swing.JButton();
        lbTimerPairMatch = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(SystemConstant.GAME);
        setResizable(false);

        plBtns.setBorder(javax.swing.BorderFactory.createTitledBorder("Chức năng"));

        btnCreateChamber.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javakit/minhphuc/caro/client/view/asset/icons8_add_24px.png"))); // NOI18N
        btnCreateChamber.setText("Create Chamber");

        btnFindMatch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javakit/minhphuc/caro/client/view/asset/icons8_circled_play_24px.png"))); // NOI18N
        btnFindMatch.setText("Start");
        btnFindMatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindMatchActionPerformed(evt);
            }
        });

        btnJoin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javakit/minhphuc/caro/client/view/asset/icons8_open_door_24px.png"))); // NOI18N
        btnJoin.setText("Join");

        btnWatch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javakit/minhphuc/caro/client/view/asset/icons8_vision_24px.png"))); // NOI18N
        btnWatch.setText("Watch");
        btnWatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWatchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout plBtnsLayout = new javax.swing.GroupLayout(plBtns);
        plBtns.setLayout(plBtnsLayout);
        plBtnsLayout.setHorizontalGroup(
            plBtnsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, plBtnsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnFindMatch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnWatch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnJoin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCreateChamber)
                .addContainerGap())
        );
        plBtnsLayout.setVerticalGroup(
            plBtnsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plBtnsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(plBtnsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCreateChamber)
                    .addComponent(btnFindMatch)
                    .addComponent(btnJoin)
                    .addComponent(btnWatch))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javakit/minhphuc/caro/client/view/asset/icons8_logout_rounded_left_24px.png"))); // NOI18N
        btnLogout.setText(SystemConstant.LOGOUT);
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        btnProfile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javakit/minhphuc/caro/client/view/asset/icons8_contact_24px.png"))); // NOI18N
        btnProfile.setText(SystemConstant.PROFILE);
        btnProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProfileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnLogout)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnProfile)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLogout)
                    .addComponent(btnProfile))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jProgressBar1.setIndeterminate(true);

        lbFindMatch.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbFindMatch.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbFindMatch.setText("Đang tìm trận...");

        jProgressBar2.setIndeterminate(true);

        btnCancelFindMatch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javakit/minhphuc/caro/client/view/asset/icons8_cancel_24px.png"))); // NOI18N
        btnCancelFindMatch.setText("Hủy");
        btnCancelFindMatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelFindMatchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout plFindingMatchLayout = new javax.swing.GroupLayout(plFindingMatch);
        plFindingMatch.setLayout(plFindingMatchLayout);
        plFindingMatchLayout.setHorizontalGroup(
            plFindingMatchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plFindingMatchLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelFindMatch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5, 5, 5))
            .addGroup(plFindingMatchLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(lbFindMatch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        plFindingMatchLayout.setVerticalGroup(
            plFindingMatchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plFindingMatchLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbFindMatch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(plFindingMatchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelFindMatch)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jProgressBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tbListChamber.setAutoCreateRowSorter(true);
        tbListChamber.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        tbListChamber.setModel(new DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbListChamber.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tbListChamber.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tbListChamber);

        btnRefreshListChamber.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javakit/minhphuc/caro/client/view/asset/icons8_replay_24px.png"))); // NOI18N
        btnRefreshListChamber.setText(SystemConstant.REFRESH);
        btnRefreshListChamber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshListChamberActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnRefreshListChamber))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRefreshListChamber)
                .addContainerGap())
        );

        tpChamberAndUser.addTab("Chambers", jPanel5);

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList1);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javakit/minhphuc/caro/client/view/asset/icons8_contact_24px.png"))); // NOI18N
        jButton1.setText("Xem thông tin");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 338, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        tpChamberAndUser.addTab("Người chơi", jPanel3);

        lbFoundMatch.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbFoundMatch.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbFoundMatch.setText("Đã tìm thấy đối thủ ... Vào ngay?");

        btnDeclinePairMatch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javakit/minhphuc/caro/client/view/asset/icons8_cancel_24px.png"))); // NOI18N
        btnDeclinePairMatch.setText("Từ chối");
        btnDeclinePairMatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeclinePairMatchActionPerformed(evt);
            }
        });

        btnAcceptPairMatch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javakit/minhphuc/caro/client/view/asset/icons8_ok_24px.png"))); // NOI18N
        btnAcceptPairMatch.setText("Chấp nhận");
        btnAcceptPairMatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcceptPairMatchActionPerformed(evt);
            }
        });

        lbTimerPairMatch.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        lbTimerPairMatch.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTimerPairMatch.setText("15s");

        javax.swing.GroupLayout plFoundMatchLayout = new javax.swing.GroupLayout(plFoundMatch);
        plFoundMatch.setLayout(plFoundMatchLayout);
        plFoundMatchLayout.setHorizontalGroup(
            plFoundMatchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plFoundMatchLayout.createSequentialGroup()
                .addGap(131, 131, 131)
                .addComponent(btnDeclinePairMatch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAcceptPairMatch)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, plFoundMatchLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(plFoundMatchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbFoundMatch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbTimerPairMatch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        plFoundMatchLayout.setVerticalGroup(
            plFoundMatchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plFoundMatchLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbFoundMatch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbTimerPairMatch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(plFoundMatchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDeclinePairMatch)
                    .addComponent(btnAcceptPairMatch))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tpChamberAndUser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(plFindingMatch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(plBtns, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(plFoundMatch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(plBtns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(plFindingMatch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(plFoundMatch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tpChamberAndUser, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        RunClient.clientController.logout();
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProfileActionPerformed

        RunClient.openScene(RunClient.Scene.PROFILE);
        RunClient.profileScene.setLoading(false);
        RunClient.profileScene.setProfileData(RunClient.clientController.getPlayer());
        RunClient.closeScene(RunClient.Scene.ARENA_HALL);

    }//GEN-LAST:event_btnProfileActionPerformed

    private void btnFindMatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindMatchActionPerformed
        // chỉ gửi yêu cầu lên Server chứ ko đổi giao diện ngay
        // clientController sẽ đọc kết quả trả về từ Server và quyết định có đổi stateDisplay hay không
        setDisplayState(State.FINDING_MATCH);
        RunClient.clientController.findMatch();
    }//GEN-LAST:event_btnFindMatchActionPerformed

    private void btnCancelFindMatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelFindMatchActionPerformed
        // chỉ gửi yêu cầu lên Server chứ ko đổi giao diện ngay
        // clientController sẽ đọc kết quả trả về từ Server và quyết định có đổi stateDisplay hay không
//        RunClient.clientController.cancelFindMatch();
    }//GEN-LAST:event_btnCancelFindMatchActionPerformed

    private void btnDeclinePairMatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeclinePairMatchActionPerformed
//        setDisplayState(State.DEFAULT);
//        RunClient.clientController.declinePairMatch();
    }//GEN-LAST:event_btnDeclinePairMatchActionPerformed

    private void btnAcceptPairMatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcceptPairMatchActionPerformed
        setDisplayState(State.WAITING_COMPETITOR_ACCEPT);
        RunClient.clientController.acceptPairMatch();
    }//GEN-LAST:event_btnAcceptPairMatchActionPerformed

    private void btnWatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWatchActionPerformed
//        // https://stackoverflow.com/a/38981623
//        int column = 0;
//        int row = tbListChamber.getSelectedRow();
//        if (row >= 0) {
//            String ChamberId = tbListChamber.getModel().getValueAt(row, column).toString();
////            RunClient.clientController.watchChamber(ChamberId);
       }
//    }//GEN-LAST:event_btnWatchActionPerformed

    private void btnRefreshListChamberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshListChamberActionPerformed
        RunClient.clientController.listChamber();
    }//GEN-LAST:event_btnRefreshListChamberActionPerformed

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
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAcceptPairMatch;
    private javax.swing.JButton btnCancelFindMatch;
    private javax.swing.JButton btnCreateChamber;
    private javax.swing.JButton btnDeclinePairMatch;
    private javax.swing.JButton btnFindMatch;
    private javax.swing.JButton btnJoin;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnProfile;
    private javax.swing.JButton btnRefreshListChamber;
    private javax.swing.JButton btnWatch;
    private javax.swing.JButton jButton1;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JProgressBar jProgressBar2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbFindMatch;
    private javax.swing.JLabel lbFoundMatch;
    private javax.swing.JLabel lbTimerPairMatch;
    private javax.swing.JPanel plBtns;
    private javax.swing.JPanel plFindingMatch;
    private javax.swing.JPanel plFoundMatch;
    private javax.swing.JTable tbListChamber;
    private javax.swing.JTabbedPane tpChamberAndUser;
    // End of variables declaration//GEN-END:variables
}
