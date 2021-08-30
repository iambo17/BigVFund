package sdb.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCollectionInfo {
    private String username;
    private List<CollectionInfo> collectionInfo;
}
