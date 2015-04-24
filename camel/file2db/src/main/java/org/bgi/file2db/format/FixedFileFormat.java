package org.bgi.file2db.format;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.camel.dataformat.flatpack.FlatpackDataFormat;
import org.apache.camel.spi.DataFormat;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

public class FixedFileFormat extends FileFormat {

	@Override
	public DataFormat toCamelDataFormat() throws Exception {
		File flatPackConfiguration = generateFilePackageConfiguration();
		FlatpackDataFormat format = new FlatpackDataFormat();
		format.setFixed(true);
		format.setDefinition(flatPackConfiguration.toURI().toASCIIString());
		return format;
	}
	
	private File generateFilePackageConfiguration() throws Exception {
		File result = File.createTempFile(this.getName(), "pzmap.xml");
		result.deleteOnExit();
		
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		DocumentType doctype = builder.getDOMImplementation().createDocumentType("doctype", 
			    "PZMAP",
			    "flatpack.dtd");
		
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		Element root = doc.createElement("PZMAP");
		doc.appendChild(root);
		
		for(ColumnFormat<?> column : this.getColumns()){
			if(column.getMessageHeaderName() == null){
				Element columnElement = doc.createElement("COLUMN");
				columnElement.setAttribute("name", column.getName());
				columnElement.setAttribute("length", String.valueOf(column.getLength()));
				root.appendChild(columnElement);
			}
		}
		
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		//transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, doctype.getPublicId());
		transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.transform(new DOMSource(doc), new StreamResult(result));
		return result;
		
	}

}
