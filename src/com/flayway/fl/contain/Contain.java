package com.flayway.fl.contain;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Map.Entry;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import com.flayway.fl.utils.FlBugPro;
import com.flayway.fl.utils.FlReadProp;
import com.flayway.fl.utils.FlReadXml;
import com.flayway.fl.utils.StringUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Contain {
	public Contain() {start();}
	public static void start() {
		JFrame jframe = new JFrame();
		jframe.setSize(400, 300);
		jframe.setLayout(new BorderLayout());
		
		JPanel jpanelB = new JPanel();
		JButton enButton = new JButton("encode");
		JButton deButton = new JButton("decode");
		
		jpanelB.add(enButton);
		jpanelB.add(deButton);
		
		JPanel jpanelTa = new JPanel();
		JPanel jpanelTb = new JPanel();
		
		Font fonta = new Font("宋体", Font.PLAIN, 12);
		
		JTextArea inputT = new JTextArea(4, 50);
		inputT.setLineWrap(true);
		inputT.setBorder(new LineBorder(null,1,false));
		inputT.setFont(fonta);
		
		JTextArea outputT = new JTextArea(4, 50);
		outputT.setLineWrap(true);
		outputT.setBorder(new LineBorder(null,1,false));
		outputT.setFont(fonta);
		
		jpanelTa.add(inputT);
		jpanelTb.add(outputT);
		
		jframe.add(jpanelB, BorderLayout.NORTH);
		jframe.add(jpanelTa, BorderLayout.CENTER);
		jframe.add(jpanelTb, BorderLayout.SOUTH);
		
		enButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				outputT.setText("");
				String inputText = StringUtils.trim(inputT.getText());
				if(StringUtils.isEmpty(inputText)) return;
				String outputText = null;
				try {
					BASE64Encoder ee = new BASE64Encoder();
					BASE64Decoder dd = new BASE64Decoder();
					byte b[] = dd.decodeBuffer(StringUtils.transforToString(FlReadProp.getPropByKey("conf.ysmyc")));
					String bm = StringUtils.transforToString(FlReadProp.getPropByKey("conf.bm"));
					String sf = StringUtils.transforToString(FlReadProp.getPropByKey("conf.sf"));
					int keySize = Integer.parseInt(StringUtils.transforToString(FlReadProp.getPropByKey("conf.size")));
					SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
					secureRandom.setSeed(b);
					KeyGenerator keyGenerator = KeyGenerator.getInstance(sf);
					keyGenerator.init(keySize, secureRandom);
					SecretKey secretKey = keyGenerator.generateKey();
					Cipher cipher = Cipher.getInstance(sf);
					cipher.init(Cipher.ENCRYPT_MODE, secretKey);
					byte[] result = cipher.doFinal(inputText.getBytes(bm));
					outputText = ee.encode(result);
				} catch(Exception ex) {
					try {
						FlBugPro.writeBug(Contain.class, 
								Contain.class.getMethod("start", new Class<?>[]{}), ex);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					outputText = "转化失败";
				}
				outputT.setText(outputText);
			}
		});
		
		deButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				outputT.setText("");
				String inputText = StringUtils.trim(inputT.getText());
				if(StringUtils.isEmpty(inputText)) return;
				String outputText = null;
				try {
					BASE64Decoder dd = new BASE64Decoder();
					byte b[] = dd.decodeBuffer(StringUtils.transforToString(FlReadProp.getPropByKey("conf.ysmyc")));
					String bm = StringUtils.transforToString(FlReadProp.getPropByKey("conf.bm"));
					String sf = StringUtils.transforToString(FlReadProp.getPropByKey("conf.sf"));
					int keySize = Integer.parseInt(StringUtils.transforToString(FlReadProp.getPropByKey("conf.size")));
					SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
					secureRandom.setSeed(b);
					KeyGenerator keyGenerator = KeyGenerator.getInstance(sf);
					keyGenerator.init(keySize, secureRandom);
					SecretKey secretKey = keyGenerator.generateKey();
					Cipher cipher = Cipher.getInstance(sf);
					cipher.init(Cipher.DECRYPT_MODE, secretKey);
					byte[] result = dd.decodeBuffer(inputText);
					byte[] reResult = cipher.doFinal(result);
					outputText = new String(reResult, bm);
				} catch(Exception ex) {
					try {
						FlBugPro.writeBug(Contain.class, 
								Contain.class.getMethod("start", new Class<?>[]{}), ex);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					outputText = "转化失败";
				}
				outputT.setText(outputText);
			}
		});
		
		JMenuBar jmenuBar = new JMenuBar();
		JMenu helpMenu = new JMenu("帮助");
		
		Map<Object, Map<Object, Object>> dbMap = FlReadXml.getDbMap();
		for(Entry<Object, Map<Object, Object>> db : dbMap.entrySet()) {
			JMenu fmenu = new JMenu(StringUtils.transforToString(db.getKey()));
			Map<Object, Object> vl = db.getValue();
			for(Entry<Object, Object> vle : vl.entrySet()) {
				JMenuItem fitem = new JMenuItem(StringUtils.transforToString(vle.getKey())+StringUtils.getHelpMenuSplit()+
						StringUtils.transforToString(vle.getValue()));
				fitem.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if(!StringUtils.isEmpty(fitem.getText())) {
							inputT.setText("");
							String vl = StringUtils.transforToString(fitem.getText());
							try {
								inputT.setText((vl.split(StringUtils.getHelpMenuSplit()))[1]);
							} catch(Exception exx) {
								inputT.setText("");
							}
						}
					}
				});
				fmenu.add(fitem);
			}
			helpMenu.add(fmenu);
		}
		
		jmenuBar.add(helpMenu);
		jframe.setJMenuBar(jmenuBar);
		
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setTitle("title");
		jframe.setLocationRelativeTo(null);
		jframe.setVisible(true);
	}
	
}
