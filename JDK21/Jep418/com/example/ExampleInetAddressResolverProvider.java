package com.example;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.spi.InetAddressResolver;
import java.net.spi.InetAddressResolverProvider;
import java.util.stream.Stream;

public class ExampleInetAddressResolverProvider extends InetAddressResolverProvider {
    @Override
    public InetAddressResolver get(Configuration configuration) {
        return new InetAddressResolver() {
            @Override
            public Stream<InetAddress> lookupByName(String host, LookupPolicy lookupPolicy) throws UnknownHostException {
                return Stream.of(InetAddress.getByAddress(host, new byte[]{1, 1, 1, 1}));
            }

            @Override
            public String lookupByAddress(byte[] addr) {
                return null;
            }
        };
    }

    @Override
    public String name() {
        return "ExampleInetAddressResolverProvider";
    }
}
