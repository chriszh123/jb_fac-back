package com.ruoyi.fac.vo.client.res;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class KanjiaListVo implements Serializable {
    private static final long serialVersionUID = -5743709623049646775L;

    private List<KanjiaItemVo> result = new ArrayList<>();

    private Map<Long, KanjiaProdVo> goodsMap = new HashMap<>();
}
