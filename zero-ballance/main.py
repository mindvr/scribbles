#!/usr/bin/env python3

def permutate(bases, base_limits, limit):
    if base_limits[0] == -1:
        eff_limit = limit
    else:
        eff_limit = min(bases[0] * base_limits[0], limit)

    if len(bases) == 1:
        return list(range(0, eff_limit // bases[0] + 1))

    vectors = []
    for x in range(0, eff_limit // bases[0] + 1):
        for v in permutate(bases[1:], base_limits[1:], limit - x * bases[0]):
            if isinstance(v, list):
                vectors.append([x] + v)
            elif isinstance(v, int):
                vectors.append([x, v])
            else:
                raise TypeError()
    return vectors


def v_length(bases, vector):
    if isinstance(vector, int):
        return bases[0] * vector
    elif isinstance(vector, list):
        assert len(bases) == len(vector)
        x = [i * j for i, j in zip(bases, vector)]
        return sum(x)
    else:
        raise TypeError()


def ways_to_zero(bases, base_limits, exact, min_add, max_add):
    assert len(bases) == len(base_limits)
    limit = exact + max_add
    permutations = permutate(bases, base_limits, limit)
    permutations.reverse()

    def match_criterion(v):
        left = exact + min_add
        l = v_length(v, bases)
        return l == exact or left <= l < limit

    return [((v_length(bases, v), v_length(bases, v) - exact), tuple(v)) for v in permutations if match_criterion(v)]


# TODOs:
# 2. input from stdin
# 4. pretty output as csv
# 6. readme

if __name__ == '__main__':
    base = [449, 199, 15, 99, 199, 249, 349, 449, 499, 699]
    base_max = [1, 1, 1, 5, 5, 5, 3, 3, 2, 1]
    s_exact, add_min, add_max = 779, 500, 550
    print(base)
    for v in ways_to_zero(base, base_max, s_exact, add_min, add_max):
        print(v)
