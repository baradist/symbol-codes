package cf.baradist.algorithms;

public enum Algorithm {
    SF {
        @Override
        public String toString() {
            return "Shannon-Fano";
        }
    },
    H {
        @Override
        public String toString() {
            return "Huffman";
        }
    }
}
