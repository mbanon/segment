package net.sourceforge.segment.srx.io;

import static net.rootnode.loomchild.util.io.Util.getReader;
import static net.rootnode.loomchild.util.io.Util.getResourceStream;
import static net.rootnode.loomchild.util.xml.Util.getTemplates;
import static net.rootnode.loomchild.util.xml.Util.transform;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Templates;

import net.sourceforge.segment.srx.SrxTransformer;

import junit.framework.TestCase;

public class SrxTransformersTest extends TestCase {

	public static final String SRX_1_DOCUMENT_NAME = "net/sourceforge/segment/res/test/example1.srx";

	public static final String SRX_2_DOCUMENT_NAME = "net/sourceforge/segment/res/test/example2.srx";

	public static final String STYLESHEET = "net/sourceforge/segment/res/xml/strip-space.xsl";

	private static final Templates templates = getTemplates(getReader(getResourceStream(STYLESHEET)));

	public void testSrx1Transformer() {
		SrxTransformer transformer = new Srx1Transformer();
		Map<String, Object> parameterMap = new HashMap<String, Object>();

		testTransformer(SRX_2_DOCUMENT_NAME, SRX_1_DOCUMENT_NAME, transformer,
				parameterMap);

		parameterMap.put(Srx1Transformer.MAP_RULE_NAME, (Object) "Default");
		testTransformer(SRX_2_DOCUMENT_NAME, SRX_1_DOCUMENT_NAME, transformer,
				parameterMap);
	}

	public void testSrx2Transformer() {
		SrxTransformer transformer = new Srx2Transformer();
		Map<String, Object> parameterMap = new HashMap<String, Object>();

		testTransformer(SRX_2_DOCUMENT_NAME, SRX_2_DOCUMENT_NAME, transformer,
				parameterMap);
	}

	private void testTransformer(String expectedDocumentName,
			String sourceDocumentName, SrxTransformer transformer,
			Map<String, Object> parameterMap) {

		Reader reader = getReader(getResourceStream(expectedDocumentName));
		String expectedDocument = removeWhitespaces(reader);

		reader = getReader(getResourceStream(sourceDocumentName));
		reader = transformer.transform(reader, parameterMap);
		String actualDocument = removeWhitespaces(reader);
		assertEquals(expectedDocument, actualDocument);

		reader = getReader(getResourceStream(sourceDocumentName));
		Writer writer = new StringWriter();
		transformer.transform(reader, writer, parameterMap);
		reader = new StringReader(writer.toString());
		actualDocument = removeWhitespaces(reader);
		assertEquals(expectedDocument, actualDocument);

	}

	private String removeWhitespaces(Reader reader) {
		StringWriter writer = new StringWriter();
		transform(templates, reader, writer);
		return writer.toString();
	}

}
