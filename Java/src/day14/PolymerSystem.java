package day14;

import java.util.Map;

public class PolymerSystem {
    private Map<String, String> rules;
    private String template;

    public PolymerSystem (String polymer, Map<String, String> rules) {
        this.template = polymer;
        this.rules = rules;
    }

    public String getTemplate() { return template; }
    public Map<String, String> getRules() { return rules; }
}
