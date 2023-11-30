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
# 2. input from stdin
# 4. pretty output as csv
# 6. readme

if __name__ == '__main__':

    base = [449, 199,  # nPlayer, PDD
            15, 99, 199, 249, 349, 449, 499, 699, # bullshit apps
            # 59, 149, 599,  # icloud 50G 200G and 2000G
            # 399,  # Podcasts
            # 449, 899,  # Duo Donates
            ]
    base_max = [1, 1,
                1, 5, 5, 5, 3, 3, 2, 1,
                # 1, 1, 1,
                # 1,
                # 1, 1
                ]
    s_exact, add_min, add_max = 779, 500, 550

    limit = s_exact + add_max
    assert len(base) == len(base_max)
    permutations = permutate(base, base_max, limit)
    permutations.reverse()
    # permutations.sort(key=lambda x: v_length(base, x))


    def match_criterion(v):
        left = s_exact + add_min
        l = v_length(v, base)
        return l == s_exact or left <= l < limit


    matches = [v for v in permutations if match_criterion(v)]

    print(base)
    for v in matches:
        l = v_length(base, v)
        print(v, l, l - s_exact)
