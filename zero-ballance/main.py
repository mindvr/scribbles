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
# 3. set exact, min add and max add
# 4. pretty output as csv
# 5. sort output by least added sum

if __name__ == '__main__':
    # limit = 779
    # base = [2, 3, 5]
    # base_max = [-1, -1, -1]
    base = [1, 3]
    base_max = [3, -1]
    # base = [3]
    # base_max = [-1]

    assert len(base) == len(base_max)
    print(base)
    for v in permutate(base, base_max, 10):
        print(v, v_length(base, v))
