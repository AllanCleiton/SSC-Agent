module sscagent.infrastructure {

    requires sscagent.domain;
    requires tools.jackson.databind;
    requires sscagent.application;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires org.apache.pdfbox;
    requires tools.jackson.dataformat.yaml;


    exports com.allancleitonppma.sscagent.infrastructure.adapters.json;
    exports com.allancleitonppma.sscagent.infrastructure.adapters.excel;
    exports com.allancleitonppma.sscagent.infrastructure.export;


    opens com.allancleitonppma.sscagent.infrastructure.dto
            to tools.jackson.databind;
    exports com.allancleitonppma.sscagent.infrastructure.adapters.pdf;
}