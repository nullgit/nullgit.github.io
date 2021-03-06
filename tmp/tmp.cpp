#include<iostream>
#include<vector>
#include<queue>
#include<string.h>
#include<set>
#include<map>
#include<ctime>
#include<cstdlib>
#include<algorithm>
#include<bitset>

using namespace std;

 struct TreeNode {
     int val;
     TreeNode *left;
     TreeNode *right;
     TreeNode(int x) : val(x), left(NULL), right(NULL) {}
 };


class Util{
    vector<int> array2vector(int arr[], int size){
        vector<int> ans;
        for (int i = 0; i < size; ++i){
            ans.push_back(arr[i]);
        }
        return ans;
    }

    TreeNode* creatTree(vector<int> arr){
        queue<TreeNode*> pre;
        TreeNode *root = TreeNode(arr[0]);
        pre.push(root);
        int index = 0;
        
        while(!pre.empty()){
            queue<TreeNode *> cur;

            while(!pre.empty()){
                TreeNode *node = pre.front();
                pre.pop();
                TreeNode *left = NULL;
                TreeNode *right = NULL;

                ++index;
                if(index<arr.size() && !arr[index]){
                    left = new TreeNode(arr[index]);
                    cur.push(left);
                }
                if(index<arr.size() && !arr[index]){
                    right = new TreeNode(arr[index]);
                    cur.push(right);
                }
                node.left = left;
                node.right = right;
            }

            pre = cur;
        }

        return root;
    }

    void printTree(TreeNode* root){
        if(!root)
            return;
        cout << root->val;
        printTree(root->left);
        printTree(root->right);
    }

    void printVectorInt(vector<int> &ans){
        for (int i = 0; i < ans.size(); ++i){
            cout << ans[i];
        }
    }

    void printVectorIntInt(vector<vector<int> > &ans){
        for (int i = 0; i < ans.size(); ++i){
            vector<int> tmp = ans[i];
            for (int j = 0; j < tmp.size(); ++j){
                cout << tmp[j] << ' ';
            }
            cout << endl;
        }
    }
};

int main(){
    return 0;
}