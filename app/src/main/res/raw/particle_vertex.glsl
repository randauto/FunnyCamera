
uniform mat4 uMVPMatrix;

attribute vec2 aPosition;
attribute vec2 aUV;

varying vec2 vUV;

void main()
{
    gl_Position = uMVPMatrix  *  vec4( aPosition, -0.001, 1);
    vUV =aUV;
}

