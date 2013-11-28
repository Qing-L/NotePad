package NotePad;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;
import javax.swing.text.*;
import javax.swing.undo.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*; 
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings({ "serial", "unused" })
class Txt extends JFrame implements  ActionListener
{
	/*关于主窗口布局的变量*/
	JFrame main = new JFrame();
	JMenuBar bar = new JMenuBar();
	JTextArea txt = new JTextArea();
	/*关于字体设置框布局的变量*/
	JLabel lb = new JLabel();
	JPanel panel1,panel2,panel3;
	JDialog dialog ;//对话框
	Font f;
	JButton btn;
	@SuppressWarnings("rawtypes")
	JComboBox  fontChoose,fontSize,fontStyle;
	/*文件菜单项*/
	JMenu file = new JMenu("文件");
	JMenuItem fileNew = new JMenuItem("新建文件");
	JMenuItem fileOpen = new JMenuItem("打开文件");
	JMenuItem fileSaving = new JMenuItem("保存");
	JMenuItem OtherSaving = new JMenuItem("另存为");
	JMenuItem fileExit = new JMenuItem("退出");
	/*编辑菜单项*/
	JMenu edit = new JMenu("编辑");
	JMenuItem editRevoke = new JMenuItem("撤销");
	JMenuItem editCut = new JMenuItem("剪切");
	JMenuItem editCopy = new JMenuItem("复制");
	JMenuItem editPaste = new JMenuItem("粘贴");
	JMenuItem editDelete = new JMenuItem("删除");
	JMenuItem editFind = new JMenuItem("查找");
	JMenuItem editReplace = new JMenuItem("替换");
	JMenuItem editCheckall = new JMenuItem("全选");
	JMenuItem editTime = new JMenuItem("时间/日期");
	/*帮助菜单项*/
	JMenu help = new JMenu("帮助");
	JMenuItem helpAbout = new JMenuItem("关于记事本");
	/*格式菜单项*/
	JMenu form = new JMenu("格式");
	JCheckBoxMenuItem linewrap = new JCheckBoxMenuItem("自动换行");
	JMenuItem color = new JMenuItem("字体");
	/*查看菜单项*/
	JMenu check = new JMenu("查看");
	JCheckBoxMenuItem statusbar = new JCheckBoxMenuItem("状态栏");
	/*右键弹出式菜单项*/
	JPopupMenu right = new JPopupMenu();
	JMenuItem rightRevoke = new JMenuItem("撤销");
	JMenuItem rightCheckall = new JMenuItem("全选");
	JMenuItem rightCut = new JMenuItem("剪切");
	JMenuItem rightCopy = new JMenuItem("复制");
	JMenuItem rightPaste = new JMenuItem("粘贴");
	JMenuItem rightDelete = new JMenuItem("删除");
	/*文件读写*/	
	FileReader r_file;//读文件
	FileWriter w_file;//写文件
	BufferedReader buf_reader;//字符输出缓冲区
	BufferedWriter buf_writer;//字符输入缓冲区
	String str = null;//用于存打开的文件的路径
	UndoManager undo = new UndoManager();
	/*构造函数*/
	Txt()
	{
		setmainwindows();
		actionthing();		
	}
	/*窗口设置*/
	public void setmainwindows()
	{
		/*文件菜单*/		
		file.add(fileNew);
		fileNew.setEnabled(true);
		file.add(fileOpen);
		fileOpen.setEnabled(true);
		file.add(fileSaving);
		fileSaving.setEnabled(true);
		file.add(OtherSaving);
		OtherSaving.setEnabled(true);
		file.addSeparator();
		file.add(fileExit);
		fileExit.setEnabled(true);
		/*编辑菜单*/	
		edit.add(editRevoke);
		editRevoke.setEnabled(false);
		edit.addSeparator();
		edit.add(editCut);
		editCut.setEnabled(true);
		edit.add(editCopy);
		editCopy.setEnabled(true);
		edit.add(editPaste);
		editPaste.setEnabled(true);
		edit.add(editDelete);
		editDelete.setEnabled(true);
		edit.addSeparator();
		edit.add(editFind);
		editFind.setEnabled(true);
		edit.add(editReplace);
		editReplace.setEnabled(true);
		edit.addSeparator();
		edit.add(editCheckall);
		editCheckall.setEnabled(true);
		edit.add(editTime);
		editTime.setEnabled(true);
		/*格式菜单*/		
		form.add(linewrap);
		linewrap.setEnabled(true);
		form.add(color);
		color.setEnabled(true);
		/*查看菜单*/
		check.add(statusbar);
		statusbar.setEnabled(false);
		/*帮助菜单*/		
		help.add(helpAbout);
		helpAbout.setEnabled(true);
		/*右键菜单*/
		aboutPullRight();
		/*菜单栏*/
		setJMenuBar(bar);
		bar.add(file);
		bar.add(edit);
		bar.add(form);
		bar.add(check);
		bar.add(help);	
		/*窗口设置*/
		main.setTitle("记事本");
		main.setSize(700,700);
		main.setVisible(true);
		main.setDefaultCloseOperation(EXIT_ON_CLOSE);				
		main.setLocationRelativeTo(null);
		main.setLayout(new BorderLayout());
		main.add("North",bar);		
		/*文本区设置*/
	    JScrollPane panel = new JScrollPane(txt,
		ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
		ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);//只有垂直方向滚动条
		main.getContentPane().add(panel, BorderLayout.CENTER);//将带滚动条的面板加入窗口中
		txt.getDocument().addUndoableEditListener(undo);
		validate();
	}
	/*监听事件*/
	public void actionthing()
	{
		fileExit.addActionListener(this);
		fileNew.addActionListener(this);
		fileOpen.addActionListener(this);
		fileSaving.addActionListener(this);
		OtherSaving.addActionListener(this);
		fileExit.addActionListener(this);
		/*----------------------------------*/
		editRevoke.addActionListener(this);
		editCut.addActionListener(this);
		editCopy.addActionListener(this);
		editPaste.addActionListener(this);
		editDelete.addActionListener(this);
		editFind.addActionListener(this);
		editReplace.addActionListener(this);
		editCheckall.addActionListener(this);
		editTime.addActionListener(this);
		/*----------------------------------*/
		linewrap.addActionListener(this);
		color.addActionListener(this);
		/*----------------------------------*/
		statusbar.addActionListener(this);
		/*----------------------------------*/
		helpAbout.addActionListener(this);
		/*----------------------------------*/
		rightRevoke.addActionListener(this);
		rightCheckall.addActionListener(this);
		rightCut.addActionListener(this);
		rightCopy.addActionListener(this);
		rightPaste.addActionListener(this);
		rightDelete.addActionListener(this);
	}
	/*关于右键菜单添加*/
	public	void aboutPullRight()
	{   
		//设置快捷键操作
		rightCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X ,  InputEvent.CTRL_MASK));
		rightCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C , InputEvent.CTRL_MASK));
		rightPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V , InputEvent.CTRL_MASK));
		rightDelete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D ,  InputEvent.CTRL_MASK));
		rightCheckall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A ,  InputEvent.CTRL_MASK));
		rightRevoke.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z ,  InputEvent.CTRL_MASK));
		/**************************/
		right.add(rightCut);
		right.add(rightCopy);
		right.add(rightPaste);
		right.add(rightDelete);
		right.addSeparator();
		right.add(rightRevoke);
		rightRevoke.setEnabled(false);
		right.add(rightCheckall);
		//在文本区内添加右键单击事件	
		txt.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if(e.getButton() == MouseEvent.BUTTON3 )
					right.show(e.getComponent(),e.getX(),e.getY());
			}
		});
	}

	public void checkTxt()
	{
		try
		{//判断Txt文件是否存在，若不存在，新建一个,isDirectory()是检查一个对象是否是文件夹
			if(!(new File("Txt/").isDirectory())) 
				new File("Txt/").mkdir(); //mkdir只创建一个文件夹
		}
		catch(SecurityException e)
		{
		      e.printStackTrace();
		}
	}

	public void checkFile()
	{//判断名为str的文件是否存在，若存在，则将其名+1再存储，直到文件名不重复
		int na = 0;
		String p= str;
		try 
		{
			while(new File("Txt/"+p+".txt").exists())//.exists()是判断一个文件是否存在
			{ 
		    	na++;
		    	p = str+"("+na+")";				
			}
		    str=p;		    
			new File("Txt/"+str+".txt").createNewFile();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();//.createNewFile();是新建一个文件
		} 
	}
	/*新建文件时，先获取文本区中的内容，当选择保存文件时，将内容默认保存至a.txt文件中
	 * 当点击取消时，清空文本区的内容，并退出子菜单*/
	public void Newfile() 
	{		
		checkTxt();
		String s = txt.getText();
		int n = JOptionPane.showConfirmDialog(null,
				"是否保存文件？","消息框",JOptionPane.YES_NO_CANCEL_OPTION);
		if(n == 0)//当点击是时，保存文本区中的内容至默认文件中
		{
			try 
			{
				str = "新建文件";
				checkFile();
				w_file = new FileWriter("Txt/"+str+".txt");
				buf_writer = new BufferedWriter(w_file);
				buf_writer.write(s, 0, s.length());
				buf_writer.flush();//刷新缓冲区中的内容
				txt.setText(null);
				str = null;
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}				
		}
		if(n == 1)//当点击否时，清空文本区
		{
			txt.setText(null);
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
		if(n==2)//当点击取消时，关闭弹出窗口，不清空文本区
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	/*打开文件时，先获取选择打开的文件，当点击确定打开该文件时，将文件内容
	 * 输入文本框中，且每次打开前都清空文本区中的内容，当点击取消时, 清空文本区，并且退出子菜单*/
	public void Openfile()
	{		
		checkTxt();
		JFileChooser file = new JFileChooser("Txt/");
		file.setAcceptAllFileFilterUsed(false);//去掉显示所有文件这个过滤器
		file.addChoosableFileFilter(new FileNameExtensionFilter("文本文件(.txt)", "txt"));
		int n = file.showOpenDialog(null);//返回0则代表选择了该文件，返回1则代表点击取消键
		str = file.getSelectedFile().toString();//获取所选择文件的路径
	    if(n==0)
	    {
			try 
			{
				File f = new File(str);
				r_file = new FileReader(f);
				buf_reader = new BufferedReader(r_file);
			} 
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
			try
			{
				String s;
				txt.setText(null);//每次新打开文件时，将上次打开的文件内容清除
				while((s=buf_reader.readLine()) != null)//按行将文件读入文本区中
				{
					txt.append(s+'\n');
				}
			}
			catch(IOException e)
			{
				System.out.println(e);
			}
	    }
		if(n==1)
		{
			txt.setText(null);
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
	}
	/*保存文件，先获取文本区中的内容，在获取对话框中键入的文件名，将文本区内容写入文件中*/
	public void savingfile() 
	{	
		checkTxt();
		String s = txt.getText();		
		if(str==null)//将新建的文本去的内容保存至自命名文件中
		{
			JFileChooser file = new JFileChooser("Txt/");
			file.setAcceptAllFileFilterUsed(false);//去掉显示所有文件这个过滤器
			file.addChoosableFileFilter(new FileNameExtensionFilter("文本文件(.txt)", "txt"));
			int n = file.showSaveDialog(null);
			str = file.getSelectedFile().getName();
            if(n==0)
            {
            	checkFile();
				try 
				{
					w_file = new FileWriter("Txt/"+str+".txt");
					buf_writer = new BufferedWriter(w_file);
					buf_writer.write(s, 0, s.length());
					buf_writer.flush();
					txt.setText(null);
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}					
				str = null;
			} 				
			else if(n==1)
			{
				txt.setText(null);
				this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
			else if(n==2)
				this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
	    else //若文本区中的内容是对打开的文件进行修改，则直接将修改内容保存至原文件中
		{
			try
			{
				w_file = new FileWriter(str);
				buf_writer = new BufferedWriter(w_file);
				buf_writer.write(s, 0, s.length());
				buf_writer.flush();
			} 
			catch (IOException e) 
			{
			    e.printStackTrace();
			}	
			str = null;
		}				
	}
	public void OtherSaving()
	{
		checkTxt();
		String s = txt.getText();		
		JFileChooser file = new JFileChooser("Txt/");
		file.setDialogTitle("另存为");
		file.setAcceptAllFileFilterUsed(false);//去掉显示所有文件这个过滤器
		file.addChoosableFileFilter(new FileNameExtensionFilter("文本文件(.txt)", "txt"));
		int n = file.showSaveDialog(null);
		str = file.getSelectedFile().getName();
        if(n==0)
        {
        	checkFile();
			try 
			{
				w_file = new FileWriter("Txt/"+str+".txt");
				buf_writer = new BufferedWriter(w_file);
				buf_writer.write(s, 0, s.length());
				buf_writer.flush();
				txt.setText(null);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}					
			str = null;
		} 				
		else if(n==1)
		{
			txt.setText(null);
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
		else if(n==2)
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
	}
	/*当选择退出时，退出软件，当选择取消时，只退出子菜单*/			
	public void exitfile()
	{		
		int n = JOptionPane.showConfirmDialog(null,"确定退出吗？",
				"退出消息框",JOptionPane.YES_NO_OPTION);
		if(n == 0)
			System.exit(0);
		if(n == 1)
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	public void revoke(ActionEvent e)
	{
		if (undo.canUndo()) 
		{
			editRevoke.setEnabled(true);
			rightRevoke.setEnabled(true);
			if(e.getSource()==editRevoke||e.getSource()==rightRevoke)
			{
				try 
				{
					undo.undo();
				} 
				catch (CannotUndoException ex) 
				{
					ex.printStackTrace();
				}
			}
		}
		else if (!undo.canUndo()) 
		{
			editRevoke.setEnabled(false);
			rightRevoke.setEnabled(false);
		}		
	}
	public void Find()
	{
		JFrame find = new JFrame("查找");
		final JTextField findfield = new JTextField(20);
		JButton findnext = new JButton("查找下一个");
		JLabel label = new JLabel("查找内容");
		find.setSize(250,200);
		find.setVisible(true);
		find.setLayout(new FlowLayout());
		find.add(label);
		find.add(findfield);
		find.add(findnext);
		findnext.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				   String ch = findfield.getText();
			        String str1 = txt.getText();
			        int end = str1.length();
			        int len = ch.length();
			        int start = txt.getSelectionEnd();
			        if(start==end) start=0;
			        if(ch.equals(""))
			        {
			        	JOptionPane.showMessageDialog(null, "要替换的串为空", 
			        			"提示信息", JOptionPane.INFORMATION_MESSAGE);
			        }
			        else
			        {
			        	for(;start<=end-len;start++)
				        {
				            if(str1.substring(start,start+len).equals(ch))
				            {
				                txt.setSelectionStart(start);
				                txt.setSelectionEnd(start+len);
				                return;
				            }
				        }
				        //若找不到待查字符串，则将光标置于末尾
				        txt.setSelectionStart(end);
				        txt.setSelectionEnd(end);
			        }			        
			}
		});		
	}
	public void Replace()
	{
		JFrame replace=new JFrame("替换");
		replace.setSize(250,200);
		replace.setVisible(true);
		final JTextField findfield=new JTextField(20);
		final JTextField replacefield=new JTextField(20);
		JButton replacenext =new JButton("替换下一个");
		JButton replaceall =new JButton("全部替换");			
	    JLabel label1=new JLabel("查找内容");
	    JLabel label2=new JLabel("替换为");
	    replace.add(label1);
	    replace.add(findfield);
	    replace.add(label2);
	    replace.add(replacefield);
	    replace.add(replacenext);
	    replace.add(replaceall);
	    replace.setLayout(new FlowLayout());
	    replacenext.addActionListener(new ActionListener()/*替换一个字符串，原理同查找，只是将查找到的替换为所替换的字符串*/
	    {
	    	public void actionPerformed(ActionEvent e)
	    	{
	    		String ch = findfield.getText();
		        String str1 = txt.getText();
		        String str2 = replacefield.getText();
		        int end = str1.length();
		        int len = ch.length();
		        int start = txt.getSelectionEnd();
		        if(start==end) start=0;
		        if(ch.equals(""))
		        {
		        	JOptionPane.showMessageDialog(null, "要替换的串为空", 
		        			"提示信息", JOptionPane.INFORMATION_MESSAGE);
		        }
		        else
		        {
		        	for(;start<=end-len;start++)
			        {
			            if(str1.substring(start,start+len).equals(ch))
			            {
			                txt.replaceRange(str2, start, start+ch.length());
			                return;
			            }
			        }
			        //若找不到待查字符串，则将光标置于末尾
			        txt.setSelectionStart(end);
			        txt.setSelectionEnd(end);
		        }			        
	    	}
	    	});
	    replaceall.addActionListener(new ActionListener()/*全部替换*/
        {
             public void actionPerformed(ActionEvent e)
             {
          	    String str1=txt.getText();
          	    String ch=findfield.getText();
          	    String str2=replacefield.getText();
          	    int i=0;/*循环变量，表示替换到第i个位置*/
          	    while (true)
          	    {//如果超出替换的范围跳出循环，由于可能替换后，原字符串长度发生改变，因此需要更新str1的长度
                   if (i>str1.length()-ch.length()) break;
          	       if(str1.substring(i,i+ch.length()).equals(ch))//如果找到所需替换的字符串
          		   {
          		       txt.replaceRange(str2, i, i+ch.length());
          		       str1=txt.getText();//读取更新后的txt区域的字符串
                   }
          	       i++;
          	    }
          	    txt.setSelectionStart(str1.length());
	    		txt.setSelectionEnd(str1.length());
             }
        });		
	}
	public void linewrap()
	{
		 if((linewrap).getState()==true)//判断是否现在自动换行 			    		
 		{ 
 		   this.txt.setLineWrap(true);  			    		
 		} 
 		else 
 		{
 			this.txt.setLineWrap(false);  		
 		}
	}
	/*设置字体函数的功能*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setfont()
	{
		dialog = new JDialog(this,"字体设置",true);
		dialog.setBounds(300,200,370,250);
		dialog.setResizable(false);
	    //下拉列表
		fontChoose = new JComboBox();
		fontSize = new JComboBox();
	    fontStyle = new JComboBox();
		JButton b0 = new JButton("预览");
		JButton b1 = new JButton("确定");
		JButton b2 = new JButton("取消");
		lb = new JLabel();
		lb.setSize(370,200);
		lb.setFont(txt.getFont());
		lb.setText("AaBbCcDd");
		
		panel1 = new JPanel(new FlowLayout());
		panel2 = new JPanel();
		panel3 = new JPanel(new FlowLayout());
		
		panel1.setSize(300,200);
		panel1.add(fontChoose);
		panel1.add(fontSize);
		panel1.add(fontStyle);
		panel2.add(lb,BorderLayout.CENTER);
		panel3.setSize(200,100);
		panel3.add(b0);
		panel3.add(b1);
		panel3.add(b2);
	    //获取系统字体集
		GraphicsEnvironment fontList =  GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] str = fontList.getAvailableFontFamilyNames();
		for(int i = 0;i < str.length;i++)
		{
			fontChoose.addItem(str[i]);
		}
		for(int j  = 8 ; j < 73 ; j++)
		{
			fontSize.addItem(j);
		}
		fontStyle.addItem("常规");
		fontStyle.addItem("粗体");
		fontStyle.addItem("斜体");
		fontStyle.addItem("斜粗体");
        //在对话框中进行布局
		dialog.add(panel1,BorderLayout.NORTH);
		dialog.add(panel2,BorderLayout.CENTER);
		dialog.add(panel3,BorderLayout.SOUTH);
		
		b0.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				String name = (String)fontChoose.getSelectedItem();
				if(fontStyle.getSelectedIndex() == 0)
				{
					
					f = new Font(name,Font.PLAIN,(int)fontSize.getSelectedItem() );
					lb.setFont(f);
				}
				if(fontStyle.getSelectedIndex() == 1)
				{
					f = new Font(name,Font.BOLD,(int)fontSize.getSelectedItem() );
					lb.setFont(f);
				}
				if(fontStyle.getSelectedIndex() == 2)
				{
					f = new Font(name,Font.ITALIC,(int)fontSize.getSelectedItem() );
					lb.setFont(f);
				}
				if(fontStyle.getSelectedIndex() == 3)
				{
					f = new Font(name,Font.BOLD+Font.ITALIC,(int)fontSize.getSelectedItem() );
					lb.setFont(f);
				}
			}
		});
		b1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				txt.setFont(f);
				dialog.setVisible(false);
			}
		});
		b2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				dialog.setVisible(false);
			}
		});
		dialog.setVisible(true);		
	}
    /*监听事件函数*/
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==fileExit)
		{
			exitfile(); 
		}
		else if(e.getSource()==fileNew)
		{
			Newfile(); 	
		}
		else if(e.getSource()==fileOpen)
		{
			Openfile(); 	
		}
		else if(e.getSource()==fileSaving)
		{
			savingfile();
		}
		else if(e.getSource()==OtherSaving)
		{
			OtherSaving();	
		}
		revoke(e);
		if(e.getSource()==editCut||e.getSource()==rightCut) 
		{
			txt.cut(); 		
		}
		else if(e.getSource()==editCopy||e.getSource()==rightCopy)
		{
			txt.copy();    	
		}
		else if(e.getSource()==editPaste||e.getSource()==rightPaste) 
		{
			txt.paste();
		}
		else if(e.getSource()==editDelete||e.getSource()==rightDelete)
		{
	        txt.replaceRange("",txt.getSelectionStart(),txt.getSelectionEnd());//删除从Txt1中被选取的文本。
		}
		else if(e.getSource()==editFind)
		{
			Find();
		}
		else if(e.getSource()==editReplace)
		{
			Replace();
		}
		else if(e.getSource()==editCheckall||e.getSource()==rightCheckall)
			txt.selectAll();
		else if(e.getSource()==editTime)
		{
			SimpleDateFormat formater = new SimpleDateFormat("HH:mm:ss  yyyy/MM/dd");
			txt.append(formater.format(new Date()));
		}
		else if(e.getSource()==linewrap)
		{
			linewrap();
		}
		else if(e.getSource()==color)
		{
			setfont();
		}
		else if(e.getSource()==helpAbout)
		{
			try 
			{
				 Runtime.getRuntime().exec("D:\\Users\\Administrator\\AppData\\Local\\Kingsoft\\"
						+ "WPS Office\\9.1.0.4249\\office6\\wps.exe "
						+"D:\\JAVA程序\\workspace\\Text\\Txt\\记事本帮助.doc");
		    } 
			catch (IOException e1) 
			{
				e1.printStackTrace();
			}
		}		
	}
	
	public void mousePressed(MouseEvent e)  {	}
	public void mouseReleased(MouseEvent e) {	}
	public void mouseEntered(MouseEvent e)  {	}
	public void mouseExited(MouseEvent e)   {  	}
 
}
public class Text 
{
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{ 
		Txt f = new Txt();
	}
}
