
.PHONY: build_and_push
build_and_push: assembly build push

.PHONY: assembly
assembly:
	rm -f target/scala-2.11/*.jar
	sbt -no-colors assembly

.PHONY: build
build:
	docker build -t  gcr.io/analytics-experiment/jim/test-bigtable-grpc/test-bigtable-grpc:$$(git describe) .
.PHONY: push
push:
	docker push  gcr.io/analytics-experiment/jim/test-bigtable-grpc/test-bigtable-grpc:$$(git describe)

.PHONY: clean
clean:
	rm -rf target project/target
