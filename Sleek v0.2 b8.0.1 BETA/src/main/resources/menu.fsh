//precision mediump float;

uniform float time;
uniform vec2 mouse;
uniform vec2 resolution;

float spike(float x) {
    x = mod(x, 2.0);

    if (x < 1.0)
    return x * x;

    x = 2.0 - x;

    return x * x;
}


float noise(float x, float amplitude) {
    float n = sin(x*8.0 + time) * 0.05 +
    sin(x*27.3 + time*0.5) * 0.005 +
    sin(time) * 0.01;
    return n * amplitude;
}

const vec3 sky_high = vec3(255, 103, 48) / 255.0;
const vec3 sky_low = vec3(255, 17, 107) / 255.0;
const vec3 water = vec3(97, 160, 171) / 255.0;

float fog = 1.0;

void main() {

    vec2 uv = (gl_FragCoord.xy / resolution.xy);
    vec2 sunuv = (gl_FragCoord.xy * 2.0 - resolution) / min(resolution.x, resolution.y);

    float v = 0.0;

    vec3 sun = vec3(179, 107, 0) / 255.0;

    if (distance(sunuv, vec2(0.0)) > 0.5)
    sun = vec3(0.0);

    float n0 = noise(uv.x * 3.18, 0.3);
    float n1 = noise(uv.x * 2.34, 0.4);
    float n2 = noise(uv.x * 2.14, 0.6);
    float n3 = noise(uv.x * 1.4, 0.8);
    float n4 = noise(uv.x * 1.23, 1.0);

    if (uv.y < 0.2 + n4) {
        v = 0.5;
    } else if (uv.y < 0.3 + n3) {
        v = 0.4;
    } else if (uv.y < 0.35 + n2) {
        v = 0.3;
    } else if (uv.y < 0.385 + n1) {
        v = 0.2;
    } else if (uv.y < 0.41 + n0) {
        v = 0.1;
    }

    vec3 color = mix(sky_low, sky_high, uv.y) + sun;

    vec3 red = vec3(1.0,0.2,0.0);
    vec2 P0 = sunuv + vec2(0.0, 0.3);
    float r = (1.0 - length(P0));
    red = red * vec3(r);

    if (v > 0.0) {
        vec3 water_color = water * v;
        color = mix(mix(sky_high, sky_low, 0.5), water_color, clamp(1000.0-(uv.y*uv.y*uv.y)*fog, 0.0, 1.0)) + red;

    }

    gl_FragColor = vec4(color, 1.0);
}