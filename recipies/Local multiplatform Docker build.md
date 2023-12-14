# Local multiplatform Docker build

```sh
docker build --platform linux/amd64 -t repository-url/image-name:amd64 .
docker build --platform linux/arm64 -t repository-url/image-name:arm64 .

docker push repository-url/image-name:amd64
docker push repository-url/image-name:arm64

docker manifest create repository-url/image-name repository-url/image-name:amd64 repository-url/image-name:arm64
docker manifest annotate repository-url/image-name repository-url/image-name:amd64 --arch amd64
docker manifest annotate repository-url/image-name repository-url/image-name:arm64 --arch arm64

docker manifest push repository-url/image-name
```