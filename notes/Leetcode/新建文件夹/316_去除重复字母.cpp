class Solution {
public:
    string removeDuplicateLetters(string s) {
        vector<int> lastIndex(26, -1);
        for (int i = 0; i < s.length(); ++i) {
            lastIndex[s[i] - 'a'] = i;
        }

        stack<int> st;
        set<int> existed;
        for (int i = 0; i < s.length(); ++i) {
            int currCh = s[i] - 'a';
            if (existed.find(currCh) == existed.end()) {
                if (!st.empty() && currCh < st.top() && lastIndex[st.top()] > i) {
                    do {
                        existed.erase(st.top());
                        st.pop();
                    } while (!st.empty() && currCh < st.top() && lastIndex[st.top()] > i);
                }
                existed.insert(currCh);
                st.push(currCh);
            }
        }

        string ans = "";
        while (!st.empty()) {
            ans += st.top() + 'a';
            st.pop();
        }
        for (int i = 0; i < ans.length() / 2; ++i) {
            char tmp = ans[i];
            ans[i] = ans[ans.length() - i - 1];
            ans[ans.length() - i - 1] = tmp;
        }
        return ans;
    }
};