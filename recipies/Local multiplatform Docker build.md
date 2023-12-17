# Local multiplatform Docker build

Running buildx build+push is once commmand, but doesn't play well with self signed certificates, 
since it runs new container that requires pushing CA inside of it. 

Doing multiplatform build w/o buildx requires more commands but otherwise is simpler: 

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

[buildy.sh](a/buildy.sh) - as a script with sensible defaults to build amd64 and arm64:

```
-i|--image-name - image name, default - current directory name
-r|--repository-url - repository url, BUILDY_REPOSITORY_URL env variable by default
-t|--tag - image tag, latest by default
```