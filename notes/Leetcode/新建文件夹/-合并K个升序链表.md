23. 合并K个升序链表

---

最小堆法：
时间复杂度：O() 空间复杂度：O()  

---

C++  

```cpp
class Solution {
public:
	struct PQnode {
		int val;
        ListNode* currPtr;
        PQnode(int v, ListNode* p) {
            val = v;
            currPtr = p;
        }
        bool operator < (const PQnode & other) const {
            return val > other.val;
        }
    };

    ListNode* mergeKLists(vector<ListNode*>& lists) {
        if (lists.size() == 0) {
            return nullptr;
        }

        priority_queue<PQnode> pq;
		for (int i = 0; i < lists.size(); ++i) {
            if (lists[i] != nullptr) {
                pq.push(PQnode(lists[i]->val, lists[i]->next));
            }
        }

		ListNode *dummy = new ListNode(0);
        ListNode *ans = dummy;
        while (!pq.empty()) {
            PQnode currNode = pq.top();
            pq.pop();
			ans->next = new ListNode(currNode.val);
            ans = ans->next;
            if (currNode.currPtr != nullptr) {
                pq.push(PQnode(currNode.currPtr->val, currNode.currPtr->next));
            }
        }
        return dummy->next;
    }
};
```
