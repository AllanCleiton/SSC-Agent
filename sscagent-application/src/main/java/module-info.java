module sscagent.application {
    requires sscagent.domain;
    exports com.allancleitonppma.sscagent.application.usecase;
    exports com.allancleitonppma.sscagent.application.ports;
    exports com.allancleitonppma.sscagent.application.services;
    exports com.allancleitonppma.sscagent.application.pickingStrategies;

}