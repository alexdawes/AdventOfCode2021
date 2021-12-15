package day13;

import java.util.List;

public class Instructions {
    private Page page;
    private List<Fold> folds;

    public Instructions (Page page, List<Fold> folds) {
        this.page = page;
        this.folds = folds;
    }

    public Page getPage() { return page; }
    public List<Fold> getFolds() { return folds; }
}
