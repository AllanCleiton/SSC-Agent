package com.allancleitonppma.sscagent.domain.model.entities;

import java.util.ArrayList;
import java.util.List;

public class InstructionInterpretation
{
    public String rawText;
    public List<ParsedCriterion> criteria = new ArrayList<>();
    public String unparsedText;
    public boolean requiresManualInterventionS;
    public String message;
}