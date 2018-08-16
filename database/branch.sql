/*
Navicat MySQL Data Transfer

Source Server         : 172.16.188.86
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : lumlum

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-07-01 22:47:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for branch
-- ----------------------------
DROP TABLE IF EXISTS `branch`;
CREATE TABLE `branch` (
  `title` varchar(20) NOT NULL,
  `code` mediumtext,
  `save_time` datetime NOT NULL,
  `username` varchar(10) NOT NULL,
  PRIMARY KEY (`title`,`username`),
  KEY `username` (`username`),
  CONSTRAINT `branch_ibfk_1` FOREIGN KEY (`username`) REFERENCES `userlist` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of branch
-- ----------------------------
INSERT INTO `branch` VALUES ('configuration', '# JDBC\\u5C5E\\u6027\njdbc.databaseName=LumLum\njdbc.username=root\njdbc.password=502717', '2018-06-05 20:31:13', 'hadoop');
INSERT INTO `branch` VALUES ('CurrentTime', '\n\npublic class Lum {\n\n	public static void main(String[] args) {\n		Calendar c=Calendar.getInstance();\n		int year=c.get(Calendar.YEAR);\n		int month=c.get(Calendar.MONTH)+1;\n		int date=c.get(Calendar.DATE);\n		int hour=c.get(Calendar.HOUR_OF_DAY);\n		int minute=c.get(Calendar.MINUTE);\n		int second=c.get(Calendar.SECOND);\n		System.out.println(year+\"/\"+month+\"/\"+date+\" \"+hour+\":\"+minute+\":\"+second);\n	}\n\n}\n', '2018-06-02 22:59:49', 'Docker');
INSERT INTO `branch` VALUES ('JTableDemo', 'import javax.swing.*;\nimport java.awt.*;\nimport java.awt.event.*;\nimport java.util.*;\n\npublic class SimpleTable {\n     \n    public SimpleTable() {\n    \n   	JFrame f = new JFrame();\n   	\n        Object[][] playerInfo = {\n            {\"阿呆\", new Integer(66), \n              new Integer(32), new Integer(98), new Boolean(false),new Boolean(false)},\n            {\"阿瓜\", new Integer(85), \n              new Integer(69), new Integer(154), new Boolean(true),new Boolean(false)},          \n        };\n\n        String[] Names = {\"姓名\", \"语文\",\"数学\",\"总分\",\"及格\",\"作弊\"};\n\n	\n        JTable table = new JTable(playerInfo, Names);\n        table.setPreferredScrollableViewportSize(new Dimension(550, 30));\n\n        JScrollPane scrollPane = new JScrollPane(table);\n\n        //f.getContentPane().add(scrollPane,BorderLayout.CENTER);\n        \n        \n        f.getContentPane().add(table.getTableHeader(),BorderLayout.NORTH);\n        f.getContentPane().add(table,BorderLayout.CENTER);\n        \n        \n	    f.setTitle(\"Simple Table\");\n        f.pack();\n        f.setVisible(true);\n        \n        f.addWindowListener(new WindowAdapter() {\n            public void windowClosing(WindowEvent e) {\n                System.exit(0);\n            }\n        });\n\n    }\n\n    public static void main(String args[]) {\n    \n        SimpleTable b = new SimpleTable();\n    }\n}\n\n', '2018-06-06 00:31:09', 'CentOS');
INSERT INTO `branch` VALUES ('mycode', 'create table mycode(\ntitle varchar(20),\ncode MEDIUMTEXT null,\nsave_time datetime not null,\nusername varchar(10),\nPRIMARY key(title,username),\nFOREIGN key(username) REFERENCES userlist(username)\n)\n', '2018-05-23 21:32:06', 'hadoop');
INSERT INTO `branch` VALUES ('one', 'Successfully Congratulations! Congratulations!', '2018-06-03 13:55:02', 'hadoop');
INSERT INTO `branch` VALUES ('two', 'Hello MyBatis 欢迎', '2018-06-06 20:44:21', 'hadoop');
