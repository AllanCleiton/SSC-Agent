module sscagent.application {
    requires sscagent.domain;
    requires sscagent.application;
    exports com.allancleitonppma.sscagent.application.usecase;
    exports com.allancleitonppma.sscagent.application.ports;

}