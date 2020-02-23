This represents a cut down test case to illuminate the library compatibility challenge of making native bigtable play nicely with spark libraries.

To run this:
* Set up a development cluster with `spark-operator` for `v2.4.5` installed.
* Edit the `Makefile` to change to your dev namespace for artifacts
* Type `make` to create and publish your container images
* Edit the `manual-test-spark-operator.yaml` to reference your container image
* Run `kubectl apply -f manual-test-spark-operator.yaml` to create the test jobs `tesla-test-0` or whatever you want to call it
* Observer that you get an error like below:


```
java.lang.IllegalStateException: Could not find TLS ALPN provider; no working netty-tcnative, Conscrypt, or Jetty NPN/ALPN available
        at io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts.defaultSslProvider(GrpcSslContexts.java:244)
        at io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts.configure(GrpcSslContexts.java:155)
        at io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts.forClient(GrpcSslContexts.java:104)
        at io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder.buildTransportFactory(NettyChannelBuilder.java:399)
        at io.grpc.internal.AbstractManagedChannelImplBuilder.build(AbstractManagedChannelImplBuilder.java:519)
        at com.google.api.gax.grpc.InstantiatingGrpcChannelProvider.createSingleChannel(InstantiatingGrpcChannelProvider.java:303)
        at com.google.api.gax.grpc.InstantiatingGrpcChannelProvider.access$1500(InstantiatingGrpcChannelProvider.java:71)
```

So the challenge is to sort out the conflicting dependencies.
