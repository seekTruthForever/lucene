package test;


import org.junit.Test;

import com.whv.doc.utils.convert.DocConverter;




public class TestDoc {

	@Test
	public void test() {
		DocConverter converter = new DocConverter("F:/powerDesignerSJK.docx");
		converter.conver();
	}

}
