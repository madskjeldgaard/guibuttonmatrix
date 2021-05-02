GuiButtonMatrix {
	var <layout;
	var rows;

	var <columnLabels;

	*new { | numRows, numCollumns, rowLabels, colLabels, buttonStates|
		^super.new.init( numRows, numCollumns, rowLabels, colLabels, buttonStates);
	}

	init { |   numRows, numCollumns, rowLabels, colLabels, buttonStates |
		var buttonWidth = 64;
		var buttonHeight = 64;
		var rect = Rect(0,0,buttonWidth, buttonHeight);

		rowLabels = rowLabels ? [];
		colLabels = colLabels ? [];

		columnLabels = HLayout(*
			numCollumns.collect{|colNum| 
				var colText = colLabels[colNum] ? "col %".format(colNum);

				StaticText.new()
				.string_(colText)
				// .align_(\center)
			}
		);

		rows = numRows.collect{|rowNum|
			var row = numCollumns.collect{|colNum|
				var butt = Button.new(bounds:rect)
				.states_(
					buttonStates ? [
						[" "],
						["x"]
					]
				)
				.action_({ 
					"row %, col %".format(rowNum, colNum).postln 
				});

				butt
			};

			row
		};

		layout = VLayout(columnLabels, *rows.collect{|row, rowIndex| 
			var labelText = rowLabels[rowIndex] ? "Row %".format(rowIndex.asString);
			var label =  StaticText.new()
			.string_(
				labelText
			);
			row = row.collect{|button|
				[button, stretch: -1]
			};

			row =  row ++ [label];
			HLayout(*row)
		});

		^this
	}

	items{
		^rows
	}

	func{|x, y, function|
		rows[y][x]
		.action_(function)
	}
}
