package com.view;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.Debt.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class VisualPlanB {

	public static void main(String[] args) throws IOException {
//		JFrame jf = new JFrame("测试窗口");          // 创建窗口
//		jf.setSize(1440, 720);                      // 设置窗口大小
//		jf.setLocationRelativeTo(null);             // 把窗口位置设置到屏幕中心
//		jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // 当点击窗口的关闭按钮时退出程序（没有这一句，程序不会退出）
//		// 2. 创建中间容器（面板容器）
//		JPanel panel = new JPanel();                // 创建面板容器，使用默认的布局管理器
//
//		// 3. 创建一个基本组件（按钮），并添加到 面板容器 中
//		JButton btn = new JButton("测试按钮");
//		panel.add(btn);
//
//		// 4. 把 面板容器 作为窗口的内容面板 设置到 窗口
//		jf.setContentPane(panel);
//
//		// 5. 显示窗口，前面创建的信息都在内存中，通过 jf.setVisible(true) 把内存中的窗口显示在屏幕上。
//		jf.setVisible(true);
		String resource = "SqlMapConfig.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		// 然后根据 sqlSessionFactory 得到 session
		SqlSession session = sqlSessionFactory.openSession();

		JFrame frameCitic = new JFrame("citic");
		frameCitic.setSize(1440,720);
		frameCitic.setLocationRelativeTo(null);
		frameCitic.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//		this.menuPanel.setBounds(0, 0, 800, 30);
//		this.menuPanel.setBackground(Color.decode("#FFFF55"));
//		this.menuPanel.setLayout((LayoutManager)null);
		JPanel panel = new JPanel();

		List<Debt> debtListTmp = session.selectList("selectAll");
		Object[][] rows = new Object[debtListTmp.size()][8];
		for (int i = 0; i < debtListTmp.size(); i++) {
			rows[i][0] = debtListTmp.get(i).getAutoID();
			rows[i][1] = debtListTmp.get(i).getID();
			rows[i][2] = debtListTmp.get(i).getName();
			rows[i][3] = debtListTmp.get(i).getNetPrice();
			rows[i][4] = debtListTmp.get(i).getClearSpeed();
			rows[i][5] = debtListTmp.get(i).getRepayDate();
			rows[i][6] = debtListTmp.get(i).getYTM();
			rows[i][7] = debtListTmp.get(i).getNumber();
		}
		String[] coloumName = {"id","证券代码","名称","净价","清算速度","偿还日期","预期收益","数量"};
		JTable debtTable = new JTable(rows,coloumName);

		// 设置表格内容颜色
		debtTable.setForeground(Color.BLACK);                   // 字体颜色
		debtTable.setFont(new Font(null, Font.PLAIN, 14));      // 字体样式
		debtTable.setSelectionForeground(Color.DARK_GRAY);      // 选中后字体颜色
		debtTable.setSelectionBackground(Color.LIGHT_GRAY);     // 选中后字体背景
		debtTable.setGridColor(Color.GRAY);                     // 网格颜色

		// 设置表头
		debtTable.getTableHeader().setFont(new Font(null, Font.BOLD, 14));  // 设置表头名称字体样式
		debtTable.getTableHeader().setForeground(Color.RED);                // 设置表头名称字体颜色
		debtTable.getTableHeader().setResizingAllowed(false);               // 设置不允许手动改变列宽
		debtTable.getTableHeader().setReorderingAllowed(false);             // 设置不允许拖动重新排序各列

		// 设置行高
		debtTable.setRowHeight(30);

		// 第一列列宽设置为40
		debtTable.getColumnModel().getColumn(0).setPreferredWidth(40);

		// 设置滚动面板视口大小（超过该大小的行数据，需要拖动滚动条才能看到）
		debtTable.setPreferredScrollableViewportSize(new Dimension(1400, 600));

		JScrollPane jScrollPane = new JScrollPane(debtTable);
		panel.add(jScrollPane);
		frameCitic.setContentPane(panel);
//		frameCitic.setContentPane(jScrollPane);
		frameCitic.setVisible(true);

		// 提交修改
		session.commit();
		// 关闭 session
		session.close();

	}



}
