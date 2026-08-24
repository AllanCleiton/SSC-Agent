module sscagent.infrastructure {
    requires sscagent.domain;
    requires tools.jackson.databind;
    requires com.allancleitonppma.sscagent.application;

    exports com.allancleitonppma.sscagent.infrastructure.json;

    opens com.allancleitonppma.sscagent.infrastructure.dto
            to tools.jackson.databind;
    }