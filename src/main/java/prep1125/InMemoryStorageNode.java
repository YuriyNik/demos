package prep1125;

import java.util.*;

class FileChunk {
    String fileId;
    int chunkId;
    byte[] data;
    FileChunk(String fileId, int chunkId, byte[] data) {
        this.fileId = fileId;
        this.chunkId = chunkId;
        this.data = data;
    }
}

interface StorageNode {
    void storeChunk(FileChunk chunk);
    FileChunk getChunk(String fileId, int chunkId);
}

class InMemoryStorageNode implements StorageNode {
    private final String nodeId;
    private final Map<String, Map<Integer, FileChunk>> storage = new HashMap<>();

    public InMemoryStorageNode(String nodeId) { this.nodeId = nodeId; }

    @Override
    public void storeChunk(FileChunk chunk) {
        storage.computeIfAbsent(chunk.fileId, k -> new HashMap<>()).put(chunk.chunkId, chunk);
    }

    @Override
    public FileChunk getChunk(String fileId, int chunkId) {
        return storage.getOrDefault(fileId, Collections.emptyMap()).get(chunkId);
    }
}

class MetadataService {
    Map<String, List<String>> fileToNodes = new HashMap<>();
    Map<String, Integer> fileChunkCount = new HashMap<>();
}

class StorageService {
    private final List<StorageNode> nodes;
    private final MetadataService metadata;

    public StorageService(List<StorageNode> nodes, MetadataService metadata) {
        this.nodes = nodes;
        this.metadata = metadata;
    }

    public void upload(String fileId, byte[] fileData, int chunkSize) {
        List<String> nodeNames = new ArrayList<>();
        int chunkId = 0;

        for (int start = 0; start < fileData.length; start += chunkSize) {
            byte[] chunk = Arrays.copyOfRange(fileData, start, Math.min(fileData.length, start + chunkSize));
            StorageNode node = nodes.get(chunkId % nodes.size());
            node.storeChunk(new FileChunk(fileId, chunkId, chunk));
            nodeNames.add(((InMemoryStorageNode)node).toString());
            chunkId++;
        }

        metadata.fileToNodes.put(fileId, nodeNames);
        metadata.fileChunkCount.put(fileId, chunkId);
    }

    public byte[] download(String fileId) {
        int count = metadata.fileChunkCount.get(fileId);
        List<byte[]> pieces = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            StorageNode node = nodes.get(i % nodes.size());
            pieces.add(node.getChunk(fileId, i).data);
        }
        return combine(pieces);
    }

    private byte[] combine(List<byte[]> parts) {
        int size = parts.stream().mapToInt(a -> a.length).sum();
        byte[] full = new byte[size];
        int pos = 0;
        for (byte[] part : parts) {
            System.arraycopy(part, 0, full, pos, part.length);
            pos += part.length;
        }
        return full;
    }

    public static void main(String[] args) {
        List<StorageNode> nodes = List.of(new InMemoryStorageNode("Node1"), new InMemoryStorageNode("Node2"));
        MetadataService meta = new MetadataService();
        StorageService service = new StorageService(nodes, meta);

        byte[] data = "Hello_Distributed_Storage".getBytes();
        service.upload("file1", data, 5);
        System.out.println(new String(service.download("file1")));
    }
}
