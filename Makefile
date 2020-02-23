
.PHONY: build_and_push
build_and_push: package build push

.PHONY: package
package:
	sbt -no-colors package

.PHONY: build
build:
	docker build -t  gcr.io/analytics-experiment/jim/test-bigtable-grpc/test-bigtable-grpc:$$(git describe) .
.PHONY: push
push:
	docker push  gcr.io/analytics-experiment/jim/test-bigtable-grpc/test-bigtable-grpc:$$(git describe)

.PHONY: clean
clean:
	rm -rf target project/target
