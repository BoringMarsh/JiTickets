package cn.edu.tongji.springbackend.dto;

import cn.edu.tongji.springbackend.model.Keywords;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetKeywordsResponse {
    private List<String> keywords;
}
