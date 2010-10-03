package org.fanhongtao.tools.xmlreader;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.fanhongtao.swt.BaseShell;
import org.fanhongtao.swt.layout.BorderData;
import org.fanhongtao.swt.layout.BorderLayout;
import org.xml.sax.SAXException;


/**
 * @author Dharma
 * @created 2008-10-22
 */
public class XMLReader extends BaseShell
{

    // 需要解析的XML文件
    private Text textXML = null;

    // 解析后的XML文件
    private Text textDetail = null;

    // 系统运行过程中的提示信息
    private Text textInfo = null;

    /*
     * (non-Javadoc)
     * 
     * @see dharma.swt.BaseShell#createContents(org.eclipse.swt.widgets.Shell)
     */
    @Override
    public void createContents(Shell shell)
    {
        shell.setLayout(new BorderLayout());
        createBottons(shell);
        createXmlText(shell);
        createInfoText(shell);
    }

    private void createBottons(Composite parent)
    {
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayoutData(BorderData.NORTH);
        composite.setLayout(new RowLayout());
        Button btnStart = new Button(composite, SWT.NONE);
        // btnStart.setLayoutData(BorderData.NORTH);
        btnStart.setText("&Start");
        btnStart.addSelectionListener(new SelectionAdapter()
        {

            @Override
            public void widgetSelected(SelectionEvent e)
            {
                try
                {
                    parseXML();
                }
                catch (Exception e1)
                {
                    textInfo.setText("Parse failed: " + e1.getMessage());
                }
            }
        });
    }

    private void createXmlText(Composite parent)
    {
        SashForm sashForm = new SashForm(parent, SWT.VERTICAL);
        sashForm.setLayoutData(BorderData.CENTER);

        // 创建输入原始XML内容的控件
        Composite composite = new Composite(sashForm, SWT.NONE);
        composite.setLayout(new GridLayout());
        Label label = new Label(composite, SWT.NONE);
        label.setText("&Original XML");

        textXML = new Text(composite, SWT.BORDER | SWT.MULTI);
        textXML.setLayoutData(new GridData(GridData.FILL_BOTH));
        textXML.setText(DETAULT_XML);

        // 创建显示分析后的XML的控件
        composite = new Composite(sashForm, SWT.NONE);
        composite.setLayout(new GridLayout());
        label = new Label(composite, SWT.NONE);
        label.setText("&Parsed XML");

        textDetail = new Text(composite, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.READ_ONLY);
        textDetail.setLayoutData(new GridData(GridData.FILL_BOTH));
    }

    private void createInfoText(Composite parent)
    {
        textInfo = new Text(parent, SWT.BORDER | SWT.READ_ONLY);
        textInfo.setLayoutData(BorderData.SOUTH);
    }

    /**
     * 使用指定的解析器，解析XML文件
     * 
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws IOException
     */
    private void parseXML() throws ParserConfigurationException, SAXException, IOException
    {
        String xml = textXML.getText();

        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        SAXParser saxParser = factory.newSAXParser();
        ByteArrayInputStream in = new ByteArrayInputStream(xml.getBytes());
        StringHandler handler = new NamespaceStringHandler();
        saxParser.parse(in, handler);

        textDetail.setText(handler.getParsedXML());
        textInfo.setText("Parse Success.");
    }

    public static void main(String[] args)
    {
        new XMLReader().run("XML Reader");
    }

    private static String DETAULT_XML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Header><tns:RequestSOAPHeader xmlns:tns=\"http://www.chinatelecom.com.cn/schema/ctcc/common/v2_1\"><tns:spId>35200002</tns:spId><tns:spPassword>AAC0A569283D0089E574364E19AC3CDB</tns:spPassword><tns:timeStamp>1020205222</tns:timeStamp><tns:productId>35200001000168</tns:productId><tns:transEnd>-1</tns:transEnd><tns:linkId>12345123451234512345</tns:linkId><tns:OA>13907550002</tns:OA><tns:FA></tns:FA><tns:multicastMessaging>false</tns:multicastMessaging></tns:RequestSOAPHeader></soapenv:Header><soapenv:Body><ns2:sendMessage xmlns:ns2=\"http://www.chinatelecom.com.cn/schema/ctcc/multimedia_messaging/send/v2_2/local\"><ns2:addresses>tel:+8613856111754</ns2:addresses><ns2:senderAddress>13907550002</ns2:senderAddress><ns2:subject></ns2:subject><ns2:priority>Default</ns2:priority></ns2:sendMessage></soapenv:Body></soapenv:Envelope>";
}
