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
	/*���������ڲ��ֵı���*/
	JFrame main = new JFrame();
	JMenuBar bar = new JMenuBar();
	JTextArea txt = new JTextArea();
	/*�����������ÿ򲼾ֵı���*/
	JLabel lb = new JLabel();
	JPanel panel1,panel2,panel3;
	JDialog dialog ;//�Ի���
	Font f;
	JButton btn;
	@SuppressWarnings("rawtypes")
	JComboBox  fontChoose,fontSize,fontStyle;
	/*�ļ��˵���*/
	JMenu file = new JMenu("�ļ�");
	JMenuItem fileNew = new JMenuItem("�½��ļ�");
	JMenuItem fileOpen = new JMenuItem("���ļ�");
	JMenuItem fileSaving = new JMenuItem("����");
	JMenuItem OtherSaving = new JMenuItem("���Ϊ");
	JMenuItem fileExit = new JMenuItem("�˳�");
	/*�༭�˵���*/
	JMenu edit = new JMenu("�༭");
	JMenuItem editRevoke = new JMenuItem("����");
	JMenuItem editCut = new JMenuItem("����");
	JMenuItem editCopy = new JMenuItem("����");
	JMenuItem editPaste = new JMenuItem("ճ��");
	JMenuItem editDelete = new JMenuItem("ɾ��");
	JMenuItem editFind = new JMenuItem("����");
	JMenuItem editReplace = new JMenuItem("�滻");
	JMenuItem editCheckall = new JMenuItem("ȫѡ");
	JMenuItem editTime = new JMenuItem("ʱ��/����");
	/*�����˵���*/
	JMenu help = new JMenu("����");
	JMenuItem helpAbout = new JMenuItem("���ڼ��±�");
	/*��ʽ�˵���*/
	JMenu form = new JMenu("��ʽ");
	JCheckBoxMenuItem linewrap = new JCheckBoxMenuItem("�Զ�����");
	JMenuItem color = new JMenuItem("����");
	/*�鿴�˵���*/
	JMenu check = new JMenu("�鿴");
	JCheckBoxMenuItem statusbar = new JCheckBoxMenuItem("״̬��");
	/*�Ҽ�����ʽ�˵���*/
	JPopupMenu right = new JPopupMenu();
	JMenuItem rightRevoke = new JMenuItem("����");
	JMenuItem rightCheckall = new JMenuItem("ȫѡ");
	JMenuItem rightCut = new JMenuItem("����");
	JMenuItem rightCopy = new JMenuItem("����");
	JMenuItem rightPaste = new JMenuItem("ճ��");
	JMenuItem rightDelete = new JMenuItem("ɾ��");
	/*�ļ���д*/	
	FileReader r_file;//���ļ�
	FileWriter w_file;//д�ļ�
	BufferedReader buf_reader;//�ַ����������
	BufferedWriter buf_writer;//�ַ����뻺����
	String str = null;//���ڴ�򿪵��ļ���·��
	UndoManager undo = new UndoManager();
	/*���캯��*/
	Txt()
	{
		setmainwindows();
		actionthing();		
	}
	/*��������*/
	public void setmainwindows()
	{
		/*�ļ��˵�*/		
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
		/*�༭�˵�*/	
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
		/*��ʽ�˵�*/		
		form.add(linewrap);
		linewrap.setEnabled(true);
		form.add(color);
		color.setEnabled(true);
		/*�鿴�˵�*/
		check.add(statusbar);
		statusbar.setEnabled(false);
		/*�����˵�*/		
		help.add(helpAbout);
		helpAbout.setEnabled(true);
		/*�Ҽ��˵�*/
		aboutPullRight();
		/*�˵���*/
		setJMenuBar(bar);
		bar.add(file);
		bar.add(edit);
		bar.add(form);
		bar.add(check);
		bar.add(help);	
		/*��������*/
		main.setTitle("���±�");
		main.setSize(700,700);
		main.setVisible(true);
		main.setDefaultCloseOperation(EXIT_ON_CLOSE);				
		main.setLocationRelativeTo(null);
		main.setLayout(new BorderLayout());
		main.add("North",bar);		
		/*�ı�������*/
	    JScrollPane panel = new JScrollPane(txt,
		ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
		ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);//ֻ�д�ֱ���������
		main.getContentPane().add(panel, BorderLayout.CENTER);//�����������������봰����
		txt.getDocument().addUndoableEditListener(undo);
		validate();
	}
	/*�����¼�*/
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
	/*�����Ҽ��˵����*/
	public	void aboutPullRight()
	{   
		//���ÿ�ݼ�����
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
		//���ı���������Ҽ������¼�	
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
		{//�ж�Txt�ļ��Ƿ���ڣ��������ڣ��½�һ��,isDirectory()�Ǽ��һ�������Ƿ����ļ���
			if(!(new File("Txt/").isDirectory())) 
				new File("Txt/").mkdir(); //mkdirֻ����һ���ļ���
		}
		catch(SecurityException e)
		{
		      e.printStackTrace();
		}
	}

	public void checkFile()
	{//�ж���Ϊstr���ļ��Ƿ���ڣ������ڣ�������+1�ٴ洢��ֱ���ļ������ظ�
		int na = 0;
		String p= str;
		try 
		{
			while(new File("Txt/"+p+".txt").exists())//.exists()���ж�һ���ļ��Ƿ����
			{ 
		    	na++;
		    	p = str+"("+na+")";				
			}
		    str=p;		    
			new File("Txt/"+str+".txt").createNewFile();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();//.createNewFile();���½�һ���ļ�
		} 
	}
	/*�½��ļ�ʱ���Ȼ�ȡ�ı����е����ݣ���ѡ�񱣴��ļ�ʱ��������Ĭ�ϱ�����a.txt�ļ���
	 * �����ȡ��ʱ������ı��������ݣ����˳��Ӳ˵�*/
	public void Newfile() 
	{		
		checkTxt();
		String s = txt.getText();
		int n = JOptionPane.showConfirmDialog(null,
				"�Ƿ񱣴��ļ���","��Ϣ��",JOptionPane.YES_NO_CANCEL_OPTION);
		if(n == 0)//�������ʱ�������ı����е�������Ĭ���ļ���
		{
			try 
			{
				str = "�½��ļ�";
				checkFile();
				w_file = new FileWriter("Txt/"+str+".txt");
				buf_writer = new BufferedWriter(w_file);
				buf_writer.write(s, 0, s.length());
				buf_writer.flush();//ˢ�»������е�����
				txt.setText(null);
				str = null;
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}				
		}
		if(n == 1)//�������ʱ������ı���
		{
			txt.setText(null);
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
		if(n==2)//�����ȡ��ʱ���رյ������ڣ�������ı���
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	/*���ļ�ʱ���Ȼ�ȡѡ��򿪵��ļ��������ȷ���򿪸��ļ�ʱ�����ļ�����
	 * �����ı����У���ÿ�δ�ǰ������ı����е����ݣ������ȡ��ʱ, ����ı����������˳��Ӳ˵�*/
	public void Openfile()
	{		
		checkTxt();
		JFileChooser file = new JFileChooser("Txt/");
		file.setAcceptAllFileFilterUsed(false);//ȥ����ʾ�����ļ����������
		file.addChoosableFileFilter(new FileNameExtensionFilter("�ı��ļ�(.txt)", "txt"));
		int n = file.showOpenDialog(null);//����0�����ѡ���˸��ļ�������1�������ȡ����
		str = file.getSelectedFile().toString();//��ȡ��ѡ���ļ���·��
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
				txt.setText(null);//ÿ���´��ļ�ʱ�����ϴδ򿪵��ļ��������
				while((s=buf_reader.readLine()) != null)//���н��ļ������ı�����
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
	/*�����ļ����Ȼ�ȡ�ı����е����ݣ��ڻ�ȡ�Ի����м�����ļ��������ı�������д���ļ���*/
	public void savingfile() 
	{	
		checkTxt();
		String s = txt.getText();		
		if(str==null)//���½����ı�ȥ�����ݱ������������ļ���
		{
			JFileChooser file = new JFileChooser("Txt/");
			file.setAcceptAllFileFilterUsed(false);//ȥ����ʾ�����ļ����������
			file.addChoosableFileFilter(new FileNameExtensionFilter("�ı��ļ�(.txt)", "txt"));
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
	    else //���ı����е������ǶԴ򿪵��ļ������޸ģ���ֱ�ӽ��޸����ݱ�����ԭ�ļ���
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
		file.setDialogTitle("���Ϊ");
		file.setAcceptAllFileFilterUsed(false);//ȥ����ʾ�����ļ����������
		file.addChoosableFileFilter(new FileNameExtensionFilter("�ı��ļ�(.txt)", "txt"));
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
	/*��ѡ���˳�ʱ���˳��������ѡ��ȡ��ʱ��ֻ�˳��Ӳ˵�*/			
	public void exitfile()
	{		
		int n = JOptionPane.showConfirmDialog(null,"ȷ���˳���",
				"�˳���Ϣ��",JOptionPane.YES_NO_OPTION);
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
		JFrame find = new JFrame("����");
		final JTextField findfield = new JTextField(20);
		JButton findnext = new JButton("������һ��");
		JLabel label = new JLabel("��������");
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
			        	JOptionPane.showMessageDialog(null, "Ҫ�滻�Ĵ�Ϊ��", 
			        			"��ʾ��Ϣ", JOptionPane.INFORMATION_MESSAGE);
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
				        //���Ҳ��������ַ������򽫹������ĩβ
				        txt.setSelectionStart(end);
				        txt.setSelectionEnd(end);
			        }			        
			}
		});		
	}
	public void Replace()
	{
		JFrame replace=new JFrame("�滻");
		replace.setSize(250,200);
		replace.setVisible(true);
		final JTextField findfield=new JTextField(20);
		final JTextField replacefield=new JTextField(20);
		JButton replacenext =new JButton("�滻��һ��");
		JButton replaceall =new JButton("ȫ���滻");			
	    JLabel label1=new JLabel("��������");
	    JLabel label2=new JLabel("�滻Ϊ");
	    replace.add(label1);
	    replace.add(findfield);
	    replace.add(label2);
	    replace.add(replacefield);
	    replace.add(replacenext);
	    replace.add(replaceall);
	    replace.setLayout(new FlowLayout());
	    replacenext.addActionListener(new ActionListener()/*�滻һ���ַ�����ԭ��ͬ���ң�ֻ�ǽ����ҵ����滻Ϊ���滻���ַ���*/
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
		        	JOptionPane.showMessageDialog(null, "Ҫ�滻�Ĵ�Ϊ��", 
		        			"��ʾ��Ϣ", JOptionPane.INFORMATION_MESSAGE);
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
			        //���Ҳ��������ַ������򽫹������ĩβ
			        txt.setSelectionStart(end);
			        txt.setSelectionEnd(end);
		        }			        
	    	}
	    	});
	    replaceall.addActionListener(new ActionListener()/*ȫ���滻*/
        {
             public void actionPerformed(ActionEvent e)
             {
          	    String str1=txt.getText();
          	    String ch=findfield.getText();
          	    String str2=replacefield.getText();
          	    int i=0;/*ѭ����������ʾ�滻����i��λ��*/
          	    while (true)
          	    {//��������滻�ķ�Χ����ѭ�������ڿ����滻��ԭ�ַ������ȷ����ı䣬�����Ҫ����str1�ĳ���
                   if (i>str1.length()-ch.length()) break;
          	       if(str1.substring(i,i+ch.length()).equals(ch))//����ҵ������滻���ַ���
          		   {
          		       txt.replaceRange(str2, i, i+ch.length());
          		       str1=txt.getText();//��ȡ���º��txt������ַ���
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
		 if((linewrap).getState()==true)//�ж��Ƿ������Զ����� 			    		
 		{ 
 		   this.txt.setLineWrap(true);  			    		
 		} 
 		else 
 		{
 			this.txt.setLineWrap(false);  		
 		}
	}
	/*�������庯���Ĺ���*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setfont()
	{
		dialog = new JDialog(this,"��������",true);
		dialog.setBounds(300,200,370,250);
		dialog.setResizable(false);
	    //�����б�
		fontChoose = new JComboBox();
		fontSize = new JComboBox();
	    fontStyle = new JComboBox();
		JButton b0 = new JButton("Ԥ��");
		JButton b1 = new JButton("ȷ��");
		JButton b2 = new JButton("ȡ��");
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
	    //��ȡϵͳ���弯
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
		fontStyle.addItem("����");
		fontStyle.addItem("����");
		fontStyle.addItem("б��");
		fontStyle.addItem("б����");
        //�ڶԻ����н��в���
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
    /*�����¼�����*/
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
	        txt.replaceRange("",txt.getSelectionStart(),txt.getSelectionEnd());//ɾ����Txt1�б�ѡȡ���ı���
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
						+"D:\\JAVA����\\workspace\\Text\\Txt\\���±�����.doc");
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
