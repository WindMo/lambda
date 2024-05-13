package org.mo.lambda.zxl;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;
import java.util.Set;

/**
 * @author WindShadow
 * @version 2024-05-13.
 */

@NoArgsConstructor
@Data
@ToString
public class Node {

    private String id;
    private String name;
    private Set<Node> in;
    private Set<Node> out;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node node = (Node) o;
        return Objects.equals(getId(), node.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
