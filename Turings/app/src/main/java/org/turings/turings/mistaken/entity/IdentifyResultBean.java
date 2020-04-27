package org.turings.turings.mistaken.entity;

import java.util.List;

public class IdentifyResultBean {
    private long log_id;
    private int words_result_num;
    private List<Words_result> words_result;

    public long getLog_id() {
        return log_id;
    }
    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }
    public int getWords_result_num() {
        return words_result_num;
    }
    public void setWords_result_num(int words_result_num) {
        this.words_result_num = words_result_num;
    }
    public List<Words_result> getWords_result() {
        return words_result;
    }
    public void setWords_result(List<Words_result> words_result) {
        this.words_result = words_result;
    }
}
