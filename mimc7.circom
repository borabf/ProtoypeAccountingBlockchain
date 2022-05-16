pragma circom 2.0.3;
include "circuits/mimc.circom";
//example MiMC7 hash merkle tree

template merkle(k){
// k is depth of tree

    signal input leaf;
    signal input paths2_root;
    signal input paths_to_root[k];

    signal output root;

    component mroot[k];
    mroot[0] = MultiMiMC7(2,91);
    mroot[0].in[0] <== leaf;
    mroot[0].in[1] <== paths2_root[0];
  
    for (var i=1; i<k-1; i++){
        mroot[i] = MultiMiMC7(2,91);
        mroot[i].in[0] <== mroot[i-1].out;
        mroot[i].in[1] <== paths_to_root[i-1];
    }
    out <== mroot[k-2].out;

   
}

component main = merkle(2);
