package poker;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PokerModel {

	//現在のチップ数　初期値は500
	int chips;

	//山札
	ArrayList<Integer> deckcards;

	//手札
	ArrayList<Integer> handcards;

	//送信ボタンに表示する文字列
	String buttonLabel;

	//プレイヤーへのメッセージ
	String message;

	//ゲーム回数
	int games;

	//コンストラクタ
	public PokerModel() {
		deckcards = new ArrayList<>();
		handcards = new ArrayList<>();

	}

	//ゲームを開始
	public void reset() {
		games = 0;
		chips = 500;
	}

	//次のゲームのためにカードを配りなおす
	public void nextgame() {
		//52枚の山札を作る
		deckcards.clear();
		for(int i=1; i<=52; i++) {
			deckcards.add(i);
		}
		Collections.shuffle(deckcards);

		//deckcards.set(0, 1);
		//deckcards.set(1, 10);
		//deckcards.set(2, 11);
		//deckcards.set(3, 12);
		//deckcards.set(4, 14);







		//山札の先頭から5枚抜いて手札にする
		handcards.clear();
		for(int i=0; i<5; i++) {
			int n = deckcards.remove(0);
			handcards.add(n);
		}

		solt();

		//残りの場札の枚数とカード番号をコンソールに表示する
		System.out.printf("%d", deckcards.size());
		for(int n: deckcards) {
			System.out.printf("%d", n);
		}
		System.out.println();

		message = "交換するカードをチェックしてください";
		buttonLabel = "交換";
		games++;
	}

	//indexで指定された位置のカードを、山札から補充したカードで置き換える
	public void change(List<String> index) {
		System.out.println("index="+index);
		for(String s: index) {
			int c = deckcards.remove(Integer.parseInt(s));     //山札の先頭を取り除き
			handcards.set(Integer.parseInt(s), c);             //手札の指定場所に入れる
		}
		//	for(int i=0; i<5; i++){
		//		if(index.contains(Integer.valueOf(i).toString())) {
		//			int c = deckcards.remove(i);
		//			handcards.set(i, c);
		//		}
		//	}
		solt();
		evaluate();
		buttonLabel = "次のゲーム";
	}

	//役の判別を行い、チップを増減させる
	void evaluate() {
		//boolean seven=false;
		//boolean red=false;
		int[] hand_num = new int[13];
		int[] suit = new int[4];
		int[] color= new int[2];


		for(int i: handcards) {
			int num=(i-1)%13+1;
			if(num==1) {
				hand_num[0] += 1;
			}else if(num==2) {
				hand_num[1] += 1;
			}else if(num==3) {
				hand_num[2] += 1;
			}else if(num==4) {
				hand_num[3] += 1;
			}else if(num==5) {
				hand_num[4] += 1;
			}else if(num==6) {
				hand_num[5] += 1;
			}else if(num==7) {
				hand_num[6] += 1;
			}else if(num==8) {
				hand_num[7] += 1;
			}else if(num==9) {
				hand_num[8] += 1;
			}else if(num==10) {
				hand_num[9] += 1;
			}else if(num==11) {
				hand_num[10] += 1;
			}else if(num==12) {
				hand_num[11] += 1;
			}else if(num==13) {
				hand_num[12] += 1;
			}

			if(1 <= i && i <= 13) {
				suit[0] += 1;
				color[0] += 1;
			}else if(14 <= i && i <= 26) {
				suit[1] += 1;
				color[1] += 1;
			}else if(27 <= i && i <= 39) {
				suit[2] += 1;
				color[1] += 1;
			}else if(40 <= i && i <= 53) {
				suit[3] += 1;
				color[0] += 1;
			}
		}

		int pair=0;
		boolean one_pair=false;
		boolean two_pair=false;
		boolean three_card=false;
		boolean four_card=false;

		//ペア
		for(int i:hand_num) {
			if(i==2) {
				pair += 1;
			}else if(i==3) {
				three_card=true;
			}else if(i==4) {
				four_card=true;
			}
		}

		if(pair==2) {
			two_pair=true;
		}else if(pair==1) {
			one_pair=true;
		}


		//フラッシュ
		boolean frash=false;
		for(int i:suit) {
			if(i==5) {
				frash=true;
			}
		}

		//ストレート
		boolean straight=false;
		for(int i=0; i<=8; i++) {
			if(hand_num[i]>=1) {
				if(hand_num[i+1]==1) {
					if(hand_num[i+2]==1) {
						if(hand_num[i+3]==1) {
							if(hand_num[i+4]==1) {
								straight=true;
								break;
							}
							break;
						}
						break;
					}
					break;
				}
				break;
			}

		}

		boolean royai=false;
		if(frash) {
			if(hand_num[0]==1) {
				if(hand_num[9]==1) {
					if(hand_num[10]==1) {
						if(hand_num[11]==1) {
							if(hand_num[12]==1) {
								royai=true;
							}
						}
					}
				}
			}
		}





		/*if(color[1]==5) {
			red=true;
		}
		if(hand_num[6]>0) {
			seven=true;
		}*/




		if(royai) {
			System.out.println("royai_frash");
			chips += 5000;
			message = "ロイヤルストレートフラッシュ:+5000";
		}else if(frash && straight) {
			System.out.println("straight_frash");
			chips += 1000;
			message = "ストレートフラッシュ:+1000";
		}else if(frash) {
			System.out.println("frash");
			chips += 500;
			message="フラッシュ:+500";
		}else if(straight) {
			System.out.println("straight");
			chips += 400;
			message="ストレート:+400";
		}else if(one_pair && three_card) {
			System.out.println("フルハウス");
			chips += 600;
			message="フルハウス:+600";
		}else if(one_pair) {
			System.out.println("one_pair");
			chips += 50;
			message="ワンペア:+50";
		}else if(two_pair) {
			System.out.println("two_pair");
			chips += 100;
			message="ツーペア:+100";
		}else if(three_card) {
			System.out.println("three_card");
			chips += 300;
			message="スリーカード:+300";
		}else if(four_card) {
			System.out.println("four_card");
			chips += 800;
			message="フォーカード:+800";
		}else {
			chips -= 100;
			message="ノーハンド:-100";
		}


		for(int i:hand_num) {
			System.out.print(i+" ");
		}
		System.out.println();





		/*if(seven && red) {
			chips += (hand_num[6] * 100) + 600;
			chip=Integer.valueOf((hand_num[6]*100)+600).toString();
			message = "セブンレッドです　+" + chip;
		}else if(seven) {
			chips += hand_num[6] * 100;
			chip=Integer.valueOf(hand_num[6]*100).toString();
			message = "セブンです　+" + chip;
		}else if(red) {
			chips += 600;
			chip=Integer.valueOf(600).toString();
			message = "レッドです　+"+chip;
		}else if(!seven && !red) {
			chips -= 100;
			message = "ノーペアです　-100";
		}

		if (chips == 0) {
			message += " ゲームオーバー";
		}*/
	}

		void solt() {
			int[] hand_card = new int[5];

			for(int i=0; i<5; i++) {
				int b=handcards.get(i);
				int a=(b-1)%13+1;
				hand_card[i]=a;
			}
			//System.out.println(handcards);
			for(int i=0; i<4; i++) {
				for(int j=0; j<4-i; j++) {
					if(hand_card[j] > hand_card[j+1]) {
						int d = hand_card[j];
						hand_card[j]=hand_card[j+1];
						hand_card[j+1]=d;

						int c = handcards.remove(j);
						handcards.add(j+1,c);
					}
				}
			}
			//System.out.println(handcards);
		}





	//JSPから呼び出されるメソッド
	public int getGames() {
		return games;
	}

	//現在のチップ数を返す
	public int getChips() {
		return chips;
	}

	//5枚の手札のうち、i番目のカードを返す（i=0～4)
	public int getHandcardAt(int i) {
		return handcards.get(i);
	}

	//送信ボタンのラベルを返す
	public String getButtonLabel() {
		return buttonLabel;
	}

	//プレイヤーへのメッセージを返す
	public String getMessage() {
		return message;
	}












}
