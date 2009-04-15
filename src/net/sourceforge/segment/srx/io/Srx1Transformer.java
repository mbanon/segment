package net.sourceforge.segment.srx.io;

import static net.rootnode.loomchild.util.io.Util.getFileInputStream;
import static net.rootnode.loomchild.util.io.Util.getFileOutputStream;
import static net.rootnode.loomchild.util.io.Util.getReader;
import static net.rootnode.loomchild.util.io.Util.getResourceStream;
import static net.rootnode.loomchild.util.io.Util.getWriter;
import static net.rootnode.loomchild.util.xml.Util.getSchema;
import static net.rootnode.loomchild.util.xml.Util.getTemplates;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;

import javax.xml.transform.Templates;
import javax.xml.validation.Schema;

import net.rootnode.loomchild.util.exceptions.IORuntimeException;
import net.rootnode.loomchild.util.xml.Util;
import net.sourceforge.segment.srx.SrxTransformer;

/**
 * Represents SRX document transformer between SRX 1.0 and newest supported
 * version. Responsible for validating input as SRX 1.0 and doing the
 * transformation using XSLT stylesheet.
 * 
 * @author loomchild
 */
public class Srx1Transformer implements SrxTransformer {

	/**
	 * Transformation parameter. Used to select map rule in SRX 1.0 document.
	 */
	public static final String MAP_RULE_NAME = "maprulename";

	private static final String STYLESHEET = "net/sourceforge/segment/res/xml/srx10.xsl";

	private static final String SCHEMA = "net/sourceforge/segment/res/xml/srx10.xsd";

	private static Templates templates = getTemplates(getReader(getResourceStream(STYLESHEET)));;

	private static Schema schema = getSchema(getReader(getResourceStream(SCHEMA)));

	/**
	 * Transform given SRX 1.0 document to newest supported version and write it
	 * to given writer. Because in current SRX version only one map rule is
	 * allowed it must be selected from SRX 1.0 document. If parameter map
	 * contains parameter {@link #MAP_RULE_NAME} then only map rule with name
	 * given by this parameter value is preserved. Otherwise first map rule from
	 * source document is preserved. If source document does not contain
	 * appropriate map rule to select, resulting document will not contain
	 * language maps and will be unusable.
	 * 
	 * @param reader
	 *            Reader containing SRX 1.0 document.
	 * @param writer
	 *            Writer to write transformed SRX document.
	 * @param parameterMap
	 *            Map containing transformation parameters.
	 */
	public void transform(Reader reader, Writer writer,
			Map<String, Object> parameterMap) {
		Util.transform(templates, schema, reader, writer, parameterMap);
	}

	/**
	 * Transforms given SRX 1.0 document and returns Reader containing SRX
	 * document in newest supported version. Creates temporary file and uses
	 * {@link #transform(Reader, Writer, Map)}.
	 * 
	 * @param reader
	 *            Reader containing SRX 1.0 document.
	 * @param parameterMap
	 *            Map containing transformation parameters.
	 * @return Returns reader containing SRX document in newest supported
	 *         version.
	 * @see #transform(Reader, Writer, Map)
	 */
	public Reader transform(Reader reader, Map<String, Object> parameterMap) {
		try {
			File file = File.createTempFile("srx2", ".srx");
			file.deleteOnExit();
			Writer writer = getWriter(getFileOutputStream(file
					.getAbsolutePath()));
			transform(reader, writer, parameterMap);
			writer.close();
			Reader resultReader = getReader(getFileInputStream(file
					.getAbsolutePath()));
			return resultReader;
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
	}

}
