#!/bin/bash

# Default values
default_image_name=$(basename "$(pwd)")
default_repository_url=${BUILDY_REPOSITORY_URL}
default_tag="latest"

# Parse CLI arguments
while [[ "$#" -gt 0 ]]; do
    case $1 in
        -i|--image-name) image_name="$2"; shift ;;
        -r|--repository-url) repository_url="$2"; shift ;;
        -t|--tag) tag="$2"; shift ;;
        *) echo "Unknown parameter passed: $1"; exit 1 ;;
    esac
    shift
done

# Set defaults if not provided
image_name=${image_name:-$default_image_name}
repository_url=${repository_url:-$default_repository_url}
tag=${tag:-$default_tag}

# Check if repository URL is provided
if [ -z "$repository_url" ]; then
    echo "Error: Repository URL not provided and BUILDY_REPOSITORY_URL is not set."
    exit 1
fi

# Building and pushing for amd64
docker build --platform linux/amd64 -t $repository_url/$image_name:amd64-$tag .
docker push $repository_url/$image_name:amd64-$tag

# Building and pushing for arm64
docker build --platform linux/arm64 -t $repository_url/$image_name:arm64-$tag .
docker push $repository_url/$image_name:arm64-$tag

docker manifest rm $repository_url/$image_name:$tag
docker manifest create -a $repository_url/$image_name:$tag \
    $repository_url/$image_name:amd64-$tag \
    $repository_url/$image_name:arm64-$tag

docker manifest annotate $repository_url/$image_name:$tag \
    $repository_url/$image_name:amd64-$tag --arch amd64

docker manifest annotate $repository_url/$image_name:$tag \
    $repository_url/$image_name:arm64-$tag --arch arm64

docker manifest push $repository_url/$image_name:$tag

echo "Build and push complete."
