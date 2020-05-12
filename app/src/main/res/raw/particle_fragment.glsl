precision mediump  float;

uniform  sampler2D  uTexture;
uniform  vec4  uColor;

varying vec2  vUV;

void main()
{
    gl_FragColor =  uColor;
}
