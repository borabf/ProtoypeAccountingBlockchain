pragma circom 2.0.0;
include "circuits/sha256/sha256_2.circom";
include "circuits/sha256/sha256.circom";

template merkle(depth){
    
    signal input leaf;
    signal input path[depth];
    signal output out;

    component mroot[depth];

    mroot[0] = Sha256_2();
    mroot[0].a <== leaf;
    mroot[0].b <== path[0];
    out <== mroot[0].out;

}

component main = merkle(2);
