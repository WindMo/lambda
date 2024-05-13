package org.mo.lambda.zxl;

import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.ToString;

/**
 * @author WindShadow
 * @version 2024-05-13.
 */
@NoArgsConstructor
@Data
@ToString
public class Consanguinity {

    public String sourceId;
    public String sourceCode;
    public String targetId;
    public String targetCode;
}
