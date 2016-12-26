#include <stdio.h>
#include <stdlib.h>

#define VISITED 0

int find_duplicate(int* list, int len) {
  int current = len;
  int next = 0;
  int previous = 0;
  int previous_val = 0;
  printlist(list, len);
  while(1) {
    next = val(list, current);    
    printf("Next index: %d\n", next);

    previous = current;
    previous_val = val(list, current);
    mark(list, current, VISITED);

    current = next;
    printf("Current: %d\n", current);
    if (val(list,current) == VISITED) {
       printf("Found duplicate at %d, value: %d\n", previous, previous_val);
      printf("Ref val: %d\n", val(list, previous));
      break;
    }
  }
  printlist(list, len);
  return current;
}


int find_duplicate_km(int* list, int len) {
  int current = 1;
  int next = 0;
  printlist(list, len);
  while(current <= len) {
    printf("Current index: %d\n", current);
    next = val(list, current);    
    if (next == current) {
      current++;
      continue;
    }
    if (val(list, next) == val(list, current)) {
        printf("Duplicate found: %d\n", val(list, current));
	break;
    }
    swap(list, current, next);
  }
  printlist(list, len);
  return current;
}


void printlist(int *list, int len) {
  while (len--) {
   printf("[%d]", *list);
   list++;
  }
  printf("\n");
}

void swap(int *list, int a, int b) {
  printf("swap %d with %d\n", list[a-1], list[b-1]);
  int tmp = list[a-1];
  list[a-1] = list[b-1];
  list[b-1] = tmp;
}


int val(int *list, int idx)  {
  printf("Checking val for: %d\n", idx);
  return list[idx - 1];
}

void mark(int *list, int idx, int loopidx) {
   list[idx-1] = loopidx;	
}

int init_data(int *list, int len, int seed) {
 int i = 0;
 // init with values
 int l = len;
 while (l-- > 0) {
  list[l] = l;
 }
 list[0] = 1;
 // do some shuffle
 for (i = seed; i  < seed + len* 10; i ++ ) {
   int a = i*i*i%len;
   int b = i*(i+1)*(i+2)%len;
   int tmp = list[a];
   list[a] = list[b];
   list[b] = tmp;
 }
}


int main(int argc, char *argv[]) {
  int len = 10;
  int * list =  malloc(len *  sizeof(int));
  int seed = 0;
  if (argc > 1) {
    seed = atoi(argv[1]);
  }
  init_data(list,len, seed);
  printf("Running O(n) algorithm M(1) space, destroying input");
//  find_duplicate(list,len);
  printf("Running Krzych-Mik O(n) M(1), destroing input - sorting it!\n");
  find_duplicate_km(list,len);
  free(list);
  return 0;
}
