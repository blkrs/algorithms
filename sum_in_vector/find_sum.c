#include <stdio.h>

int va[] = { 1,3,4,5,7,10,20 };
int vb[] = { 1,3,4,4 };
int vc[] = { 1,3,4,5 };

int find_sum(int *v, int l, int sum) {
  int i =0;
  for (i = 0; i < l; ++i) {
     printf("[%d]\n", v[i]);	  
  }
  int left = 0;
  int right = l - 1;
  while (left < right) {
    printf("l = %d, r = %d, %d %d\n", left, right, v[left], v[right]);
    if (v[left] + v[right] == sum) {
      printf("%d %d\n", v[left], v[right]);
      return 1;
    }
    if (v[right] + v[left] > sum) {
      right--;
    } else (left++);
  }
  printf("Not found\n");
  return 0;
}

int main(int argc, char *argv[]) {
 int i;
 if (argc == 1) {
 find_sum(va,7,9);
 find_sum(vb,4,9); 
 find_sum(vc,4,9); 
 } else {
   int l = argc - 2;
   int sum = atoi(argv[argc-1],10);
   printf("Searching for sum: %d\n", sum);
   int * v = malloc(sizeof(int) * (l));
   for (i = 0; i < l; ++i) {
     v[i] = atoi(argv[i+1], 10);
   }
   find_sum(v, l, sum);
   free(v);
 }
 return 0;
}
