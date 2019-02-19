#include <stdio.h>
// Convenience function for checking CUDA runtime API results
// can be wrapped around any runtime API call. No-op in release builds.


inline
cudaError_t checkCuda(cudaError_t result)
{
	#if defined(DEBUG) || defined(_DEBUG)
	if (result != cudaSuccess) {
		fprintf(stderr, "CUDA Runtime Error: %s\n", cudaGetErrorString(result));
		assert(result == cudaSuccess);
	}
	#endif
		return result;
}


__global__ void add( int a, int b, int *c ) {
	*c = a + b;
}


int main( void ) {
	int c;
	int *dev_c;
	checkCuda( cudaMalloc( (void**)&dev_c, sizeof(int) ) );
	add<<<1,1>>>( 2, 7, dev_c );

	checkCuda( cudaMemcpy( &c, dev_c, sizeof(int), cudaMemcpyDeviceToHost ) );

	printf( "2 + 7 = %d\n", c );
	checkCuda( cudaFree( dev_c ) );
	return 0;
}
