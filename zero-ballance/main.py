#!/usr/bin/env python3

def permutate(base, base_max, limit):
    if base_max[0] == -1:
        eff_limit = limit
    else:
        eff_limit = min(base[0] * base_max[0], limit)

    if len(base) == 1:
        return list(range(0, eff_limit // base[0] + 1))

    vectors = []
    for x in range(0, eff_limit // base[0] + 1):
        for v in permutate(base[1:], base_max[1:], limit - x * base[0]):
            if isinstance(v, list):
                vectors.append([x] + v)
            elif isinstance(v, int):
                vectors.append([x, v])
            else:
                raise TypeError()
    return vectors


def v_length(base, v):
    if isinstance(v, int):
        return base[0] * v
    elif isinstance(v, list):
        assert len(base) == len(v)
        x = [i * j for i, j in zip(base, v)]
        return sum(x)
    else:
        raise TypeError()

# TODOs:
# 1. fill with realistic data
# 2. input from stdin
# 4. pretty output as csv

if __name__ == '__main__':
    # limit = 779
    base = [2, 3, 5]
    base_max = [4, -1, -1]
    # base = [1, 3]
    # base_max = [3, -1]
    s_exact, add_min, add_max = 10, 5, 10
    limit = s_exact + add_max
    # base = [3]
    # base_max = [-1]


    assert len(base) == len(base_max)
    permutations = permutate(base, base_max, limit)
    permutations.sort(key=lambda x: v_length(base, x))

    def match_criterion(v):
        left = s_exact + add_min
        l = v_length(v, base)
        return l == s_exact or left <= l < limit
    matches = [v for v in permutations if match_criterion(v)]

    print(base)
    for v in matches:
        l = v_length(base, v)
        print(v, l, l - s_exact)
